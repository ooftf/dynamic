package com.ooftf.marionette.nr.view

import android.view.View
import com.ooftf.fake.myapplication.nr.BaseNodeRender
import com.ooftf.marionette.nr.NodeRenderContext

class ViewNodeRender(context: NodeRenderContext) : BaseNodeRender<View>(context) {
    override fun createView(): View {
        return View(context.context)
    }
}