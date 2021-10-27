package com.ooftf.marionette.nr.tab_layout

import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.ooftf.fake.myapplication.nr.BaseNodeRender
import com.ooftf.marionette.nr.NodeRenderContext
import org.json.JSONObject

class TabLayoutNode(context: NodeRenderContext) : BaseNodeRender<TabLayout>(context) {
    override fun createView(): TabLayout {
        return TabLayout(context.context)
    }

    override fun parse(parent: ViewGroup, json: JSONObject): View {
        super.parse(parent, json)
        view?.let {tab->
            if(json.has("items")){
                val items = json.getJSONArray("items")
                (0 until items.length()).forEach{
                    tab.addTab(tab.newTab().setText( items.getString(it)))
                }
            }
        }
        return view!!
    }

}