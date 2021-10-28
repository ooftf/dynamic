package com.ooftf.marionette.node.tab_content

import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.INodeCreator
import com.ooftf.marionette.node.NodeContext

class TabContentNodeCreator:INodeCreator {
    override fun createRender(context: NodeContext): INode {
        return TabContentNode(context)
    }
}