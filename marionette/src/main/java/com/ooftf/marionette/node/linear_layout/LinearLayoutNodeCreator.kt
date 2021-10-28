package com.ooftf.marionette.node.linear_layout

import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.INodeCreator
import com.ooftf.marionette.node.NodeContext

class LinearLayoutNodeCreator: INodeCreator {
    override fun createRender(context: NodeContext): INode {
        return LinearLayoutNode(context)
    }
}