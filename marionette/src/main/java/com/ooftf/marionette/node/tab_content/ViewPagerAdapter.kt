package com.ooftf.marionette.node.tab_content

import androidx.core.util.getOrElse
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.json.JSONArray

class ViewPagerAdapter:FragmentStateAdapter {
    var data = JSONArray()
    var parent:TabContentNode
    constructor(context:TabContentNode,fragmentActivity: FragmentActivity) : super(fragmentActivity){
        this.parent = context
    }
    constructor(context:TabContentNode,fragment: Fragment) : super(fragment){
        this.parent = context
    }
    constructor(context:TabContentNode,fragmentManager: FragmentManager, lifecycle: Lifecycle) : super(
        fragmentManager,
        lifecycle
    ){
        this.parent = context
    }
    fun updateData(newData:JSONArray){
        data = newData
    }
    override fun getItemCount(): Int {
        return data.length()
    }

    override fun createFragment(position: Int): Fragment {
        val node = parent.childNodeRenders.getOrElse(position) {
            val childrenData = data.getJSONObject(position)
            val nodeRender = parent.context.createNodeByType(childrenData.getString("type"))
            nodeRender.setParentNode(parent)
            parent.childNodeRenders.put(position, nodeRender)
            nodeRender
        }
        return ItemFragment(node,data.getJSONObject(position))
    }
}