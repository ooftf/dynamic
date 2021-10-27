package com.ooftf.marionette.nr.tab_content

import androidx.viewpager2.widget.ViewPager2
import com.ooftf.fake.myapplication.nr.BaseNodeRender
import com.ooftf.marionette.nr.NodeRenderContext
import org.json.JSONObject

class TabContentNode(context: NodeRenderContext) : BaseNodeRender<ViewPager2>(context) {
    override fun createView(): ViewPager2 {
        return ViewPager2(context.context)
    }

    override fun handleChildren(rootViewJsonObj: JSONObject) {
        view?.apply {
            TODO("adapter = FragmentStateAdapter()")
        }
    }

}