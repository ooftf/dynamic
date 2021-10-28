package com.ooftf.marionette.node.view

import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.INodeCreator
import com.ooftf.marionette.node.NodeContext

class ViewNodeRenderCreator: INodeCreator {
    override fun createRender(context: NodeContext): INode {
        return ViewNode(context)
    }
}