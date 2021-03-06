package com.ooftf.marionette.node

import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import com.ooftf.basic.utils.toast
import com.ooftf.marionette.node.banner.BannerNodeCreator
import com.ooftf.marionette.node.coordinator.CoordinatorLayoutNodeCreator
import com.ooftf.marionette.node.image.ImageNodeCreator
import com.ooftf.marionette.node.linear_layout.LinearLayoutNodeCreator
import com.ooftf.marionette.node.recycler_view.RecyclerViewNodeCreator
import com.ooftf.marionette.node.tab_content.TabContentNodeCreator
import com.ooftf.marionette.node.tab_layout.TabLayoutCreator
import com.ooftf.marionette.node.text.TextNodeCreator
import com.ooftf.marionette.node.view.ViewNode
import com.ooftf.marionette.node.view.ViewNodeCreator
import com.ooftf.marionette.node.waterfall.WaterfallNodeCreator
import com.ooftf.marionette.template.Template
import org.json.JSONObject
import org.w3c.dom.Node

// 每个 activity 对应一个  NodeRenderContext  实例
// 每个 fragment 对应一个 NodeRenderContext 实例？ 这个还没想好
class NodeContext(val context: Context) {
    var rootNode: INode? = null
    val nodeCreatorMap = HashMap<String, INodeCreator>()
    val eventHandles = HashMap<String, (JSONObject)->Unit>()
    val templateMap = SparseArray<Template>()
    init {
        registerNodeCreator("LinearLayout", LinearLayoutNodeCreator())
        registerNodeCreator("TextView", TextNodeCreator())
        registerNodeCreator("View", ViewNodeCreator())
        registerNodeCreator("TabLayout", TabLayoutCreator())
        registerNodeCreator("TabContent", TabContentNodeCreator())
        registerNodeCreator("RecyclerView", RecyclerViewNodeCreator())
        registerNodeCreator("ImageView", ImageNodeCreator())
        registerNodeCreator("Banner", BannerNodeCreator())
        registerNodeCreator("Waterfall", WaterfallNodeCreator())
        registerNodeCreator("CoordinatorLayout", CoordinatorLayoutNodeCreator())

        eventHandles["openUrl"] = {
            val url = it.getString("url")
            toast(url)
        }

        eventHandles["toast"] = {
            val url = it.getString("content")
            toast(url)
        }
    }

    fun registerEventHandler(action:String,handler:(JSONObject)->Unit){
        eventHandles[action] = handler
    }
    fun findNodeById(id: String): INode? {
        return rootNode?.findNodeRenderById(id)
    }

    fun createNodeByType(key: String): INode {
        Log.e("createNodeRenderByType","${ key}:: ${nodeCreatorMap[key].toString()}")
        return nodeCreatorMap[key]?.createRender(this)?: ViewNode(this)
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
        val nodeRender = createNodeByType(type)
        rootNode = nodeRender
        return nodeRender.parse(parent,viewData)
    }


    fun registerNodeCreator(key: String, creator: INodeCreator) {
        nodeCreatorMap.put(key, creator)
    }

    fun dispatchEvent(action:String,params:JSONObject){
        eventHandles.get(action)?.invoke(params)
    }
    val loadMoreListeners = ArrayList<(INode)->Unit>()
    fun addLoadMoreListener(listener:(INode)->Unit){
        loadMoreListeners.add(listener)
    }
    fun removeLoadMoreListener(listener:(INode)->Unit){
        loadMoreListeners.remove(listener)
    }
    val refreshListeners = ArrayList<(INode)->Unit>()
    fun addRefreshListener(listener:(INode)->Unit){
        refreshListeners.add(listener)
    }
    fun removeRefreshListener(listener:(INode)->Unit){
        refreshListeners.remove(listener)
    }
}