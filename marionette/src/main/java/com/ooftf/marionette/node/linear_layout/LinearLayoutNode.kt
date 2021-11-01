package com.ooftf.marionette.node.linear_layout

import android.view.Gravity
import android.widget.LinearLayout
import com.ooftf.marionette.node.BaseNode
import com.ooftf.marionette.node.NodeContext
import org.json.JSONObject

class LinearLayoutNode(context: NodeContext) :
    BaseNode<LinearLayout>(context) {
    override fun createView(): LinearLayout {
        return LinearLayout(context.context)
    }
    override fun handleLayoutAttrs(view: LinearLayout, layout: JSONObject) {
        super.handleLayoutAttrs(view, layout)
        if(layout.has("orientation")){
            when(layout.getString("orientation")){
                "horizontal"->view.orientation = LinearLayout.HORIZONTAL
                "vertical"->view.orientation = LinearLayout.VERTICAL
            }
        }
        handleAttr(layout,"gravity"){
            when (it){
                "top"->view.gravity = Gravity.TOP
                "left"-> view.gravity = Gravity.LEFT
                "right"-> view.gravity = Gravity.RIGHT
                "bottom"-> view.gravity = Gravity.BOTTOM
                "center"-> view.gravity = Gravity.CENTER
                "center_vertical"-> view.gravity = Gravity.CENTER_VERTICAL
                "center_horizontal"-> view.gravity = Gravity.CENTER_HORIZONTAL
            }
        }
    }
}