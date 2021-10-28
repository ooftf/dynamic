package com.ooftf.marionette.lp

import android.view.ViewGroup
import android.widget.LinearLayout
import com.ooftf.fake.myapplication.UnitParser
import com.ooftf.marionette.lp.MarginLayoutParamsParser
import org.json.JSONObject

class LinearLayoutParamsParser: MarginLayoutParamsParser() {

    override fun apply(lp: ViewGroup.LayoutParams, jsonObj: JSONObject) {
        super.apply(lp, jsonObj)
        if(jsonObj.has("weight")){
            val weight = jsonObj.getString("weight")
            (lp as LinearLayout.LayoutParams).weight = weight.toFloat()
        }
    }
}