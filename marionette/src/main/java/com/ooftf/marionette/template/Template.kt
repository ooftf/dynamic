package com.ooftf.marionette.template

import android.view.ViewGroup
import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.NodeContext
import org.json.JSONObject

class Template(val data:JSONObject,val context:NodeContext) {
    val id:Int = data.getInt("id")
    fun createView(parent:ViewGroup?):INode{
        val viewJson = data.getJSONObject("view")
        val type = viewJson.getString("type")
        val node = context.createNodeRenderByType(type)
        node.parse(parent,viewJson)
        return node
    }
}