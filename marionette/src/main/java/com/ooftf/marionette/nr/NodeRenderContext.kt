package com.ooftf.marionette.nr

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.ooftf.marionette.nr.linear_layout.LinearLayoutNodeRenderCreator
import com.ooftf.marionette.nr.tab_content.TabContentNodeCreator
import com.ooftf.marionette.nr.tab_layout.TabLayoutCreator
import com.ooftf.marionette.nr.text.TextNodeCreator
import com.ooftf.marionette.nr.view.ViewNodeRender
import com.ooftf.marionette.nr.view.ViewNodeRenderCreator
import org.json.JSONObject

// 每个 activity 对应一个  NodeRenderContext  实例
// 每个 fragment 对应一个 NodeRenderContext 实例？ 这个还没想好
class NodeRenderContext(val context: Context) {
    var rootNodeRender: INodeRender? = null
    val nodeRenderCreatorMap = HashMap<String, INodeRenderCreator>()
    init {
        registerNodeRenderCreator("LinearLayout", LinearLayoutNodeRenderCreator())
        registerNodeRenderCreator("TextView", TextNodeCreator())
        registerNodeRenderCreator("View", ViewNodeRenderCreator())
        registerNodeRenderCreator("TabLayout", TabLayoutCreator())
        registerNodeRenderCreator("TabContent", TabContentNodeCreator())
        //registerNodeRenderCreator("View", ViewNodeRenderCreator())
    }

    fun findNodeRenderById(id: String): INodeRender? {
        return rootNodeRender?.findNodeRenderById(id)
    }

    fun createNodeRenderByType(key: String): INodeRender {
        return nodeRenderCreatorMap[key]?.createRender(this)?: ViewNodeRender(this)
    }

    fun parse(parent: ViewGroup,jsonString: String): View {
        return parse(parent,JSONObject(jsonString))
    }

    fun parse(parent: ViewGroup,data: JSONObject): View {
        val type = data.getString("type")
        val nodeRender = createNodeRenderByType(type)
        rootNodeRender = nodeRender
        return nodeRender.parse(parent,data)

    }


    fun registerNodeRenderCreator(key: String, creator: INodeRenderCreator) {
        nodeRenderCreatorMap.put(key, creator)
    }
}