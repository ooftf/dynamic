package com.ooftf.marionette.node.recycler_view

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.marionette.node.BaseNode
import com.ooftf.marionette.node.NodeContext
import org.json.JSONObject

open class RecyclerViewNode(context: NodeContext) : BaseNode<RecyclerView>(context) {
    override fun createView(): RecyclerView {
        return RecyclerView(context.context)
    }

    override fun handleChildren(rootViewJsonObj: JSONObject) {
        if(!rootViewJsonObj.has("children")){
            return
        }

        val children = rootViewJsonObj.getJSONArray("children")
        view?.let {
            Log.e("RecyclerView","init")
            it.layoutManager = genLayoutManager(it)


            it.adapter = Adapter(context,children,this)
        }
    }

    open fun genLayoutManager(recyclerView: RecyclerView): RecyclerView.LayoutManager {
        return LinearLayoutManager(context.context,LinearLayoutManager.VERTICAL,false)
    }
}