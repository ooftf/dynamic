package com.ooftf.marionette.node

interface INodeCreator {
    fun createRender(context: NodeContext): INode
}