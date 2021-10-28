package com.ooftf.marionette.node.recycler_view

import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.INodeCreator
import com.ooftf.marionette.node.NodeContext

class RecyclerViewNodeCreator:INodeCreator {
    override fun createRender(context: NodeContext): INode {
        return RecyclerViewNode(context)
    }
}