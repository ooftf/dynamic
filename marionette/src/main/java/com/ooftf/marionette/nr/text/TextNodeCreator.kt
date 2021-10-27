package com.ooftf.marionette.nr.text

import com.ooftf.marionette.nr.INodeRender
import com.ooftf.marionette.nr.INodeRenderCreator
import com.ooftf.marionette.nr.NodeRenderContext

class TextNodeCreator: INodeRenderCreator {
    override fun createRender(context: NodeRenderContext): INodeRender {
        return TextNode(context)
    }
}