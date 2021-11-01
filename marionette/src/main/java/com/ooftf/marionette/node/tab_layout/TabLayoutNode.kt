package com.ooftf.marionette.node.tab_layout

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.ooftf.marionette.node.BaseNode
import com.ooftf.marionette.node.NodeContext
import org.json.JSONObject

class TabLayoutNode(context: NodeContext) : BaseNode<TabLayout>(context) {

    override fun createView(): TabLayout {
        return TabLayout(context.context)
    }
    override fun handleAttrs(view: TabLayout, json: JSONObject) {
        super.handleAttrs(view, json)
        view.let {tab->
            if(json.has("items")){
                val items = json.getJSONArray("items")
                (0 until items.length()).forEach{
                    tab.addTab(tab.newTab().setText( items.getString(it)))
                }
            }
        }
    }
    var textColor = 0
    var selectedTextColor = 0
    override fun handleLayoutAttrs(view: TabLayout, layout: JSONObject) {
        super.handleLayoutAttrs(view, layout)
        handleAttr(layout,"textColor"){
            textColor = Color.parseColor(it)
            view.setTabTextColors(textColor,selectedTextColor)
        }
        handleAttr(layout,"selectedTextColor"){
            selectedTextColor = Color.parseColor(it)
            view.setTabTextColors(textColor,selectedTextColor)
        }
    }

}