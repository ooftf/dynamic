package com.ooftf.marionette.nr

interface INodeRenderCreator {
    fun createRender(context: NodeRenderContext): INodeRender
}