package com.ooftf.marionette.node.tab_content

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ooftf.basic.utils.findAttachedFragment
import com.ooftf.basic.utils.getFragmentActivity
import com.ooftf.marionette.node.BaseNode
import com.ooftf.marionette.node.NodeContext
import org.json.JSONObject

class TabContentNode(context: NodeContext) : BaseNode<ViewPager2>(context) {
    override fun createView(): ViewPager2 {
        return ViewPager2(context.context)
    }


    override fun handleAttrs(view: ViewPager2, json: JSONObject) {
        super.handleAttrs(view, json)
        if(json.has("tabLayoutId")){
            val  tabLayoutId = json.getString("tabLayoutId")
            context.findNodeById(tabLayoutId)?.let {
                val tabLayout = it.getTargetView()
                if(tabLayout is TabLayout){
                    TabLayoutMediator(tabLayout,this.view!!){tab,position->
                        val items =  it.getModel()?.getJSONArray("items")
                        tab.text =  items?.getString(position)
                    }.attach()
                }
            }
        }
    }
    override fun handleChildren(rootViewJsonObj: JSONObject) {
        view?.let {
            var adapter : ViewPagerAdapter
            val fragment = it.findAttachedFragment()
            if(fragment!=null){
                adapter = ViewPagerAdapter(this,fragment)
            }else{
                adapter = ViewPagerAdapter(this,it.context.getFragmentActivity()!!)
            }
            adapter.updateData(rootViewJsonObj.getJSONArray("children"))
            it.adapter = adapter
        }
    }

}