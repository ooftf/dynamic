package com.ooftf.marionette.nr.linear_layout

import com.ooftf.marionette.nr.INodeRender
import com.ooftf.marionette.nr.INodeRenderCreator
import com.ooftf.marionette.nr.NodeRenderContext
import com.ooftf.marionette.nr.linear_layout.LinearLayoutNodeRender

class LinearLayoutNodeRenderCreator: INodeRenderCreator {
    override fun createRender(context: NodeRenderContext): INodeRender {
        return LinearLayoutNodeRender(context)
    }
}