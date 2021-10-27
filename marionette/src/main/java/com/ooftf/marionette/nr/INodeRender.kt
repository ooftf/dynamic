package com.ooftf.marionette.nr

import android.view.View
import android.view.ViewGroup
import org.json.JSONObject

interface INodeRender {
    fun parse(parent: ViewGroup,jsonString:String):View{
        return parse(parent,JSONObject(jsonString))
    }
    fun parse(parent: ViewGroup, json:JSONObject):View
    fun findNodeRenderById(id: String): INodeRender?
    fun getParentNode(): INodeRender?
    fun setParentNode(parent: INodeRender)
    fun getChildNode():ArrayList<INodeRender>
}