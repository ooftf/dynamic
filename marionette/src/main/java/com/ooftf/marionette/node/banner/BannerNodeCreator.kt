package com.ooftf.marionette.node.banner

import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.INodeCreator
import com.ooftf.marionette.node.NodeContext

class BannerNodeCreator:INodeCreator {
    override fun createRender(context: NodeContext): INode {
        return BannerNode(context)
    }
}