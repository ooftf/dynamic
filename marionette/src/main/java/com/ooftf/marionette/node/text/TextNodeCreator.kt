package com.ooftf.marionette.node.text

import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.INodeCreator
import com.ooftf.marionette.node.NodeContext

class TextNodeCreator: INodeCreator {
    override fun createRender(context: NodeContext): INode {
        return TextNode(context)
    }
}