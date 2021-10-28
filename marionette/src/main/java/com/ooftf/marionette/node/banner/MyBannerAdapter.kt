package com.ooftf.marionette.node.banner

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.NodeContext
import com.youth.banner.adapter.BannerAdapter
import org.json.JSONObject

class MyBannerAdapter(val context: NodeContext, datas: MutableList<JSONObject>?) : BannerAdapter<JSONObject, VH>(datas) {
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): VH {
        val node = context.findTemplateById(viewType).createView(parent)
        return VH(node.getTargetView()!!,node)
    }

    override fun onBindView(holder: VH, data: JSONObject, position: Int, size: Int) {
        val field = data.getJSONObject("field")
        field.keys().forEach {
            holder.node.injectField(it,field.getString(it))
        }
    }
    override fun getItemViewType(position: Int): Int {
        return getData(getRealPosition(position)).getInt("templateId")
    }
}


class VH(itemView: View, val node: INode):RecyclerView.ViewHolder(itemView){

}