package com.ooftf.marionette.node.tab_layout

import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.INodeCreator
import com.ooftf.marionette.node.NodeContext

class TabLayoutCreator: INodeCreator {
    override fun createRender(context: NodeContext): INode {
        return TabLayoutNode(context)
    }
}