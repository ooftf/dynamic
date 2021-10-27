package com.ooftf.marionette.nr.linear_layout

import android.widget.LinearLayout
import com.ooftf.fake.myapplication.nr.BaseNodeRender
import com.ooftf.marionette.nr.NodeRenderContext
import org.json.JSONObject

class LinearLayoutNodeRender(context: NodeRenderContext) :
    BaseNodeRender<LinearLayout>(context) {
    override fun createView(): LinearLayout {
        return LinearLayout(context.context)
    }
    override fun handleAttr(view: LinearLayout, layout: JSONObject) {
        super.handleAttr(view, layout)
        if(layout.has("orientation")){
            when(layout.getString("orientation")){
                "horizontal"->view.orientation = LinearLayout.HORIZONTAL
                "vertical"->view.orientation = LinearLayout.VERTICAL
            }
        }
    }
}