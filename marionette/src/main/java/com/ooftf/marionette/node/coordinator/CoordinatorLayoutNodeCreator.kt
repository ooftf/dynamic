package com.ooftf.marionette.node.coordinator

import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.INodeCreator
import com.ooftf.marionette.node.NodeContext

class CoordinatorLayoutNodeCreator:INodeCreator {
    override fun createRender(context: NodeContext): INode {
        return CoordinatorLayoutNode(context)
    }
}