package com.ooftf.marionette.nr.tab_layout

import com.ooftf.marionette.nr.INodeRender
import com.ooftf.marionette.nr.INodeRenderCreator
import com.ooftf.marionette.nr.NodeRenderContext

class TabLayoutCreator: INodeRenderCreator {
    override fun createRender(context: NodeRenderContext): INodeRender {
        return TabLayoutNode(context)
    }
}