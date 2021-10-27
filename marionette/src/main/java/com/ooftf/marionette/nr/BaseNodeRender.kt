package com.ooftf.fake.myapplication.nr

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.ooftf.fake.myapplication.UnitParser
import com.ooftf.fake.myapplication.lp.LayoutParamsParserManager
import com.ooftf.marionette.nr.INodeRender
import com.ooftf.marionette.nr.NodeRenderContext
import org.json.JSONObject

abstract class BaseNodeRender<T : View>(val context: NodeRenderContext) : INodeRender {
    var view: T? = null
    var data: JSONObject? = null
    var childNodeRenders = ArrayList<INodeRender>()
    var parent : INodeRender? = null
    override fun parse(parent: ViewGroup, json: JSONObject): View {
        data = json
        var result = createView()
        view = result
        if(json.has("layout")){
            var layout = json.getJSONObject("layout")
            handleAttr(result, layout)
            handleLayoutParams(result, parent, layout)
        }
        handleChildren(json)
        return result
    }

    override fun findNodeRenderById(id: String): INodeRender? {
        val local = data ?: return null
        if (local.getString("id") == id) {
            return this
        }
        childNodeRenders.forEach {
            val childNodeRender = it.findNodeRenderById(id)
            if (childNodeRender != null) {
                return childNodeRender
            }
        }
        return null

    }

    override fun getParentNode(): INodeRender? {
        return parent
    }


    override fun getChildNode(): ArrayList<INodeRender> {
        return childNodeRenders
    }

    open fun handleAttr(view: T, layout: JSONObject) {
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
        parent: ViewGroup,
        layout: JSONObject
    ) {
        val lp = getLayoutParams(result, parent)
        LayoutParamsParserManager.findParse(lp)?.apply(lp, layout)
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

    open protected fun handleChildren(
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
            childNodeRenders.add(nodeRender)
            val childrenView = nodeRender.parse(parent,childrenData)
            parent.addView(childrenView)
        }
    }

    override fun setParentNode(parent: INodeRender) {
        this.parent = parent
    }
}