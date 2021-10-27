package com.ooftf.fake.myapplication.lp

import android.view.ViewGroup
import com.ooftf.fake.myapplication.UnitParser
import org.json.JSONObject

open class MarginLayoutParamsParser: LayoutParamsParser() {
    override fun apply(lp: ViewGroup.LayoutParams, jsonObj: JSONObject) {
        super.apply(lp, jsonObj)
        val real = lp as ViewGroup.MarginLayoutParams
        if(jsonObj.has("margin")){
            val margin = jsonObj.getJSONArray("margin")
            real.setMargins(
                UnitParser.stringToPx(margin.getString(0)), UnitParser.stringToPx(margin.getString(1))
            , UnitParser.stringToPx(margin.getString(2)), UnitParser.stringToPx(margin.getString(3))
            )
        }
    }

}