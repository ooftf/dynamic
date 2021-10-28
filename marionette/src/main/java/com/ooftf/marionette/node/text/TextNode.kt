package com.ooftf.marionette.node.text

import android.graphics.Color
import android.view.Gravity
import android.widget.TextView
import com.ooftf.fake.myapplication.UnitParser
import com.ooftf.marionette.node.BaseNode
import com.ooftf.marionette.node.NodeContext
import org.json.JSONObject

class TextNode(context: NodeContext) : BaseNode<TextView>(context) {
    override fun createView(): TextView {
        return TextView(context.context)
    }

    override fun handleAttrs(view: TextView, layout: JSONObject) {
        super.handleAttrs(view, layout)
        handleAttr(layout,"text"){
            view.text = it
        }

        handleAttr(layout,"textColor"){
            view.setTextColor(Color.parseColor(it))
        }
        handleAttr(layout,"textSize"){
            view.textSize = UnitParser.stringToPx(it).toFloat()
        }
        handleAttr(layout,"gravity"){
            when (it){
                "top"->view.gravity = Gravity.TOP
                "left"-> view.gravity = Gravity.LEFT
                "right"-> view.gravity = Gravity.RIGHT
                "bottom"-> view.gravity = Gravity.BOTTOM
                "center"-> view.gravity = Gravity.CENTER
            }
        }


    }


}