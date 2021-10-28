package com.ooftf.marionette.node.image

import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.INodeCreator
import com.ooftf.marionette.node.NodeContext

class ImageNodeCreator:INodeCreator {
    override fun createRender(context: NodeContext): INode {
        return ImageNode(context)
    }
}