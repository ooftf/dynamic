package com.ooftf.marionette.nr.tab_content

import com.ooftf.marionette.nr.INodeRender
import com.ooftf.marionette.nr.INodeRenderCreator
import com.ooftf.marionette.nr.NodeRenderContext

class TabContentNodeCreator:INodeRenderCreator {
    override fun createRender(context: NodeRenderContext): INodeRender {
        return TabContentNode(context)
    }
}