package com.ooftf.marionette.node.banner

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.ooftf.marionette.node.BaseNode
import com.ooftf.marionette.node.NodeContext
import com.ooftf.marionette.node.recycler_view.Adapter
import com.youth.banner.Banner
import org.json.JSONObject

class BannerNode(context: NodeContext) : BaseNode<Banner<JSONObject,MyBannerAdapter>>(context) {
    override fun createView(): Banner<JSONObject, MyBannerAdapter> {
        return Banner<JSONObject,MyBannerAdapter>(context.context)
    }
    override fun handleChildren(rootViewJsonObj: JSONObject) {
        if(!rootViewJsonObj.has("children")){
            return
        }

        val children = rootViewJsonObj.getJSONArray("children")
        view?.let {
            val data = ArrayList<JSONObject>()
            (0 until children.length()).forEach {
                data.add(children.getJSONObject(it))
            }
            it.setAdapter(MyBannerAdapter(context,data))
        }
    }

}