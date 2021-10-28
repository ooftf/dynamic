package com.ooftf.marionette.node

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import org.json.JSONObject

interface INode {
    fun parse(parent: ViewGroup,jsonString:String):View{
        return parse(parent,JSONObject(jsonString))
    }
    fun parse(parent: ViewGroup?, json:JSONObject):View
    fun findNodeRenderById(id: String): INode?
    fun getParentNode(): INode?
    fun setParentNode(parent: INode)
    fun getChildNode(): SparseArray<INode>
    fun getTargetView():View?
    fun getModel():JSONObject?
    fun getBinder():Binder
    fun injectField(key:String,value:String)
}