package com.ooftf.marionette.node

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.core.util.remove
import com.ooftf.fake.myapplication.UnitParser
import com.ooftf.marionette.lp.LayoutParamsParserManager
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import org.json.JSONArray
import org.json.JSONObject
import java.util.regex.Pattern

abstract class BaseNode<T : View>(val context: NodeContext) : INode {
    var view: T? = null
    var data: JSONObject? = null
    var childNodeRenders = SparseArray<INode>()
    var parent : INode? = null
    var mBinder = Binder()
    var refreshLayout:SmartRefreshLayout?=null
    override fun parse(parent: ViewGroup?, json: JSONObject): View {
        data = json
        view = createView()
        var result:View = view!!
        view?.let { view->
            if(json.has("layout")){
                val layout = json.getJSONObject("layout")
                handleLayoutAttrs(view, layout)
                handleLayoutParams(result, parent, layout)
            }

        }

        if(json.has("events")){
            handleEvents(json.getJSONArray("events"))
        }

        handleChildren(json)
        handleAttrs(view!!,json)
        if(json.has("refresh")|| json.has("loadMore")){
            val smart = SmartRefreshLayout(context.context)
            refreshLayout = smart
            result.layoutParams?.let {
                smart.layoutParams = ViewGroup.LayoutParams(it.width,it.height)
            }
            smart.addView(result)
            result = smart
            if(json.has("refresh")){
                smart.setEnableRefresh(json.getBoolean("refresh"))
            }else{
                smart.setEnableRefresh(false)
            }
            if(json.has("loadMore")){
                smart.setEnableLoadMore(json.getBoolean("loadMore"))
            }else{
                smart.setEnableLoadMore(false)
            }

            smart.setOnLoadMoreListener {
                TODO("实现全局事件监听")
            }
            smart.setOnRefreshListener {
                TODO("实现全局事件监听")
            }
        }
        return result
    }

    protected open fun handleAttrs(view: T, json: JSONObject) {

    }

    private fun handleEvents(jsonArray: JSONArray) {
        (0 until jsonArray.length()).forEach {
            val item = jsonArray.getJSONObject(it)
            val trigger = item.getString("trigger")
            val action = item.getString("action")
            var params = if(item.has("params")){
                item.getJSONObject("params")
            }else{
                JSONObject()
            }
            if(trigger == "click"){
                view?.setOnClickListener {
                    context.dispatchEvent(action,params)
                }
            }
        }
    }

    override fun getBinder(): Binder {
        return  mBinder
    }
    override fun getModel(): JSONObject? {
        return data
    }
    override fun findNodeRenderById(id: String): INode? {
        val local = data ?: return null
        if (local.has("id")&&local.getString("id") == id) {
            return this
        }
        childNodeRenders.forEach {key, value ->
        val childNodeRender = value.findNodeRenderById(id)
            if (childNodeRender != null) {
                return childNodeRender
            }
        }
        return null

    }

    override fun getParentNode(): INode? {
        return parent
    }

    override fun getTargetView(): View? {
        return view
    }
    override fun getChildNode(): SparseArray<INode> {
        return childNodeRenders
    }

    open fun handleLayoutAttrs(view: T, layout: JSONObject) {
        if(layout.has("background-color")){
            val background = layout.getString("background-color")
            if(layout.has("radius")){
                val radius = layout.getJSONArray("radius")
                val bg = GradientDrawable()
                bg.setColor(Color.parseColor(background))
                val topLeft = UnitParser.stringToPxFloat(radius.getString(0))
                val topRight = UnitParser.stringToPxFloat(radius.getString(1))
                val bottomRight = UnitParser.stringToPxFloat(radius.getString(2))
                val bottomLeft = UnitParser.stringToPxFloat(radius.getString(3))
                bg.cornerRadii = floatArrayOf(topLeft, topLeft, topRight, topRight,
                    bottomRight, bottomRight, bottomLeft, bottomLeft)
                view.background = bg
            }else{
                view.setBackgroundColor(Color.parseColor(background))
            }

        }
        if(layout.has("padding")){
            val paddings = layout.getJSONArray("padding")
            view.setPadding(UnitParser.stringToPx(paddings.getString(0)),UnitParser.stringToPx(paddings.getString(1)),UnitParser.stringToPx(paddings.getString(2)),UnitParser.stringToPx(paddings.getString(3)))
        }
    }

    abstract fun createView(): T
    private fun handleLayoutParams(
        result: View,
        parent: ViewGroup?,
        layout: JSONObject
    ) {
        parent?.let {
            val lp = getLayoutParams(result, parent)
            LayoutParamsParserManager.findParse(lp)?.apply(lp, layout)
        }

    }

    fun getLayoutParams(view: View, parent: ViewGroup): ViewGroup.LayoutParams {
        if (view.layoutParams != null) {
            return view.layoutParams
        } else {
            var lp = reflectGenerateDefaultLayoutParams(parent)
            view.layoutParams = lp
            return lp
        }
    }

    fun reflectGenerateDefaultLayoutParams(parent: ViewGroup): ViewGroup.LayoutParams {
        return ViewGroup::class.java.getDeclaredMethod("generateDefaultLayoutParams").apply {
            isAccessible = true
        }
            .invoke(parent) as ViewGroup.LayoutParams
    }

    protected open fun handleChildren(
        rootViewJsonObj: JSONObject
    ) {
        if (!rootViewJsonObj.has("children")) return
        val children = rootViewJsonObj.getJSONArray("children")
        if (view !is ViewGroup) return
        val parent = view as ViewGroup
        (0 until children.length()).forEach {
            val childrenData = children.getJSONObject(it)
            val nodeRender = context.createNodeRenderByType(childrenData.getString("type"))
            nodeRender.setParentNode(this)
            childNodeRenders.put(it,nodeRender)
            val childrenView = nodeRender.parse(parent,childrenData)
            parent.addView(childrenView)
        }
    }

    override fun setParentNode(parent: INode?) {
        this.parent = parent
    }

    fun isExpression(value:String): Boolean {
        return Pattern.matches("@\\{.*\\}",value)
    }

    fun getExpressionValue(value:String):String{
        return value.subSequence(2,value.length-1).toString()
    }
    fun handleAttr(json:JSONObject, key:String, action:(String)->Unit){
        if(json.has(key)){
            val value = json.getString(key)
            if(isExpression(value)){
                getBinder().put(getExpressionValue(value),action)
            }else{
                action.invoke(value)
            }
        }
    }

    override fun injectField(key: String, value: String) {
        getBinder().update(key,value)
        getChildNode().forEach { _, node ->
            node.injectField(key,value)
        }
    }

    override fun addChildNode(position: Int, child: INode) {
        childNodeRenders.put(position,child)
        child.setParentNode(this)
    }

    override fun removeChildNode(position: Int,child: INode) {
       if( childNodeRenders.remove(position,child)){
           child.setParentNode(null)
       }
    }

    fun clear(){
        view = null
        data = null
        childNodeRenders.clear()
        mBinder.clear()
    }
}