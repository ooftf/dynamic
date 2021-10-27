package com.ooftf.marionette.nr.text

import android.widget.TextView
import com.ooftf.fake.myapplication.nr.BaseNodeRender
import com.ooftf.marionette.nr.NodeRenderContext
import org.json.JSONObject

class TextNode(context: NodeRenderContext) : BaseNodeRender<TextView>(context) {
    override fun createView(): TextView {
        return TextView(context.context)
    }

    override fun handleAttr(view: TextView, layout: JSONObject) {
        super.handleAttr(view, layout)
        val text = layout.getString("text")
        view.text = text
    }
}