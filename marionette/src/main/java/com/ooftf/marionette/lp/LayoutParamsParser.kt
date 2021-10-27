package com.ooftf.fake.myapplication.lp

import android.view.ViewGroup
import com.ooftf.fake.myapplication.UnitParser
import org.json.JSONObject

open class LayoutParamsParser:ILayoutParamsParser {
    override fun apply(lp: ViewGroup.LayoutParams, jsonObj: JSONObject) {
        if(jsonObj.has("width")){
            val width = jsonObj.getString("width")
            lp.width = UnitParser.stringToPx(width)
        }
        if(jsonObj.has("height")){
            val height = jsonObj.getString("height")
            lp.height = UnitParser.stringToPx(height)
        }
    }
}