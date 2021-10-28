package com.ooftf.marionette.node.view

import android.view.View
import com.ooftf.marionette.node.BaseNode
import com.ooftf.marionette.node.NodeContext

class ViewNode(context: NodeContext) : BaseNode<View>(context) {
    override fun createView(): View {
        return View(context.context)
    }
}