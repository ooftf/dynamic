package com.ooftf.marionette.node.waterfall

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ooftf.marionette.node.NodeContext
import com.ooftf.marionette.node.recycler_view.RecyclerViewNode
import org.json.JSONObject

class WaterfallNode(context: NodeContext) : RecyclerViewNode(context) {
    var spanCount = 2
    override fun handleLayoutAttrs(view: RecyclerView, layout: JSONObject) {
        super.handleLayoutAttrs(view, layout)
        handleAttr(layout,"spanCount"){
            (view.layoutManager as? StaggeredGridLayoutManager)?.let { sglm->
                sglm.spanCount = it.toInt()
            }
        }
    }
    override fun genLayoutManager(): RecyclerView.LayoutManager {
        val layoutManager = StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE;
        return layoutManager
    }
}