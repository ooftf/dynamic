package com.ooftf.marionette.node.waterfall

import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.INodeCreator
import com.ooftf.marionette.node.NodeContext

class WaterfallNodeCreator:INodeCreator {
    override fun createRender(context: NodeContext): INode {
        return WaterfallNode(context)
    }
}