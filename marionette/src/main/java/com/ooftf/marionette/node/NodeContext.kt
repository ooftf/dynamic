package com.ooftf.marionette.node

import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import com.ooftf.marionette.node.banner.BannerNodeCreator
import com.ooftf.marionette.node.image.ImageNodeCreator
import com.ooftf.marionette.node.linear_layout.LinearLayoutNodeCreator
import com.ooftf.marionette.node.recycler_view.RecyclerViewNodeCreator
import com.ooftf.marionette.node.tab_content.TabContentNodeCreator
import com.ooftf.marionette.node.tab_layout.TabLayoutCreator
import com.ooftf.marionette.node.text.TextNodeCreator
import com.ooftf.marionette.node.view.ViewNode
import com.ooftf.marionette.node.view.ViewNodeRenderCreator
import com.ooftf.marionette.template.Template
import org.json.JSONObject

// 每个 activity 对应一个  NodeRenderContext  实例
// 每个 fragment 对应一个 NodeRenderContext 实例？ 这个还没想好
class NodeContext(val context: Context) {
    var rootNode: INode? = null
    val nodeRenderCreatorMap = HashMap<String, INodeCreator>()
    val templateMap = SparseArray<Template>()
    init {
        registerNodeRenderCreator("LinearLayout", LinearLayoutNodeCreator())
        registerNodeRenderCreator("TextView", TextNodeCreator())
        registerNodeRenderCreator("View", ViewNodeRenderCreator())
        registerNodeRenderCreator("TabLayout", TabLayoutCreator())
        registerNodeRenderCreator("TabContent", TabContentNodeCreator())
        registerNodeRenderCreator("RecyclerView", RecyclerViewNodeCreator())
        registerNodeRenderCreator("ImageView", ImageNodeCreator())
        registerNodeRenderCreator("Banner", BannerNodeCreator())

    }

    fun findNodeRenderById(id: String): INode? {
        return rootNode?.findNodeRenderById(id)
    }

    fun createNodeRenderByType(key: String): INode {
        Log.e("createNodeRenderByType","${ key}:: ${nodeRenderCreatorMap[key].toString()}")
        return nodeRenderCreatorMap[key]?.createRender(this)?: ViewNode(this)
    }

    fun findTemplateById(id:Int): Template {
        return templateMap[id]!!
    }
    fun parse(parent: ViewGroup,jsonString: String): View {
        return parse(parent,JSONObject(jsonString))
    }

    fun parse(parent: ViewGroup,data: JSONObject): View {
        val templates = data.getJSONArray("templates")
        (0 until templates.length()).forEach {
           val template = Template(templates.getJSONObject(it),this)
            templateMap.put(template.id,template)
        }

        val viewData  = data.getJSONObject("view")
        val type = viewData.getString("type")
        val nodeRender = createNodeRenderByType(type)
        rootNode = nodeRender
        return nodeRender.parse(parent,viewData)

    }


    fun registerNodeRenderCreator(key: String, creator: INodeCreator) {
        nodeRenderCreatorMap.put(key, creator)
    }
}