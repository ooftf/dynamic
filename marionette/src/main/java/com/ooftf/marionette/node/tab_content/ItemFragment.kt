package com.ooftf.marionette.node.tab_content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ooftf.marionette.node.INode
import org.json.JSONObject

class ItemFragment(val node: INode, val jsonObject: JSONObject) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = node.getTargetView()
        if(view == null){
            view = node.parse(container,jsonObject)
        }
        return view
    }
}