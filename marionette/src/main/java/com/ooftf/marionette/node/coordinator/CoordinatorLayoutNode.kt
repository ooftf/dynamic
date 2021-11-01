package com.ooftf.marionette.node.coordinator

import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.ooftf.marionette.node.BaseNode
import com.ooftf.marionette.node.NodeContext
import org.json.JSONObject

class CoordinatorLayoutNode(context: NodeContext) : BaseNode<CoordinatorLayout>(context) {
    override fun createView(): CoordinatorLayout {
        return CoordinatorLayout(context.context)
    }


    override fun handleAttrs(coordinator: CoordinatorLayout, json: JSONObject) {
        super.handleAttrs(coordinator, json)
        val appBarLayout = AppBarLayout(context.context)
        coordinator.addView(
            appBarLayout,
            CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.WRAP_CONTENT
            )
        )
        //val lp = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        //lp.behavior = AppBarLayout.Behavior()
        if (json.has("header")) {
            val header = json.getJSONObject("header")
            val node = context.createNodeRenderByType(header.getString("type"))
            addChildNode(0, node)
            val headView = node.parse(appBarLayout, header)
            appBarLayout.addView(headView)
            val lp = headView.layoutParams as AppBarLayout.LayoutParams
            lp.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
        }
        if (json.has("sticky")) {
            val sticky = json.getJSONObject("sticky")
            val node = context.createNodeRenderByType(sticky.getString("type"))
            addChildNode(1, node)
            val stickyView = node.parse(appBarLayout, sticky)
            appBarLayout.addView(stickyView)
            val lp = stickyView.layoutParams as AppBarLayout.LayoutParams
            lp.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
        }
        if (json.has("body")) {
            val body = json.getJSONObject("body")
            val node = context.createNodeRenderByType(body.getString("type"))
            addChildNode(2, node)
            val bodyView = node.parse(coordinator, body)
            coordinator.addView(bodyView)
            val lp = bodyView.layoutParams as CoordinatorLayout.LayoutParams
            lp.behavior = AppBarLayout.ScrollingViewBehavior()
        }
    }
}