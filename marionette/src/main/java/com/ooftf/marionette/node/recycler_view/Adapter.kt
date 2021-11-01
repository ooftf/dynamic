package com.ooftf.marionette.node.recycler_view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.NodeContext
import org.json.JSONArray

class Adapter(val context:NodeContext, val data:JSONArray,val recyclerNode:INode): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val node = context.findTemplateById(viewType).createView(parent)
        return ViewHolder(node.getTargetView()!!,node)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val field = data.getJSONObject(position).getJSONObject("field")
        field.keys().forEach {
            holder.node.injectField(it,field.getString(it))
        }
    }

    override fun getItemCount(): Int {
        return data.length()
    }

    override fun getItemViewType(position: Int): Int {
        return data.getJSONObject(position).getInt("templateId")
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        recyclerNode.addChildNode(holder.bindingAdapterPosition,holder.node)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        recyclerNode.removeChildNode(holder.bindingAdapterPosition,holder.node)

    }
}

class ViewHolder(itemView: View,val node:INode) : RecyclerView.ViewHolder(itemView) {

}