package com.ooftf.marionette.nr.view

import com.ooftf.marionette.nr.INodeRender
import com.ooftf.marionette.nr.INodeRenderCreator
import com.ooftf.marionette.nr.NodeRenderContext

class ViewNodeRenderCreator: INodeRenderCreator {
    override fun createRender(context: NodeRenderContext): INodeRender {
        return ViewNodeRender(context)
    }
}