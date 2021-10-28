package com.ooftf.marionette.node

import android.graphics.Color
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import com.ooftf.fake.myapplication.UnitParser
import com.ooftf.marionette.lp.LayoutParamsParserManager
import org.json.JSONObject
import java.util.regex.Pattern

abstract class BaseNode<T : View>(val context: NodeContext) : INode {
    var view: T? = null
    var data: JSONObject? = null
    var childNodeRenders = SparseArray<INode>()
    var parent : INode? = null
    var mBinder = Binder()
    override fun parse(parent: ViewGroup?, json: JSONObject): View {
        data = json
        var result = createView()
        view = result
        if(json.has("layout")){
            var layout = json.getJSONObject("layout")
            handleAttrs(result, layout)
            handleLayoutParams(result, parent, layout)
        }
        handleChildren(json)
        return result
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

    open fun handleAttrs(view: T, layout: JSONObject) {
        if(layout.has("background-color")){
            val background = layout.getString("background-color")
            view.setBackgroundColor(Color.parseColor(background))
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

    override fun setParentNode(parent: INode) {
        this.parent = parent
    }

    fun isExpression(value:String): Boolean {
        return Pattern.matches("@\\{.*\\}",value)
    }

    fun getName(value:String):String{
        return value.subSequence(2,value.length-1).toString()
    }
    fun handleAttr(json:JSONObject, key:String, action:(String)->Unit){
        if(json.has(key)){
            val value = json.getString(key)
            if(isExpression(value)){
                getBinder().put(getName(value),action)
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
}