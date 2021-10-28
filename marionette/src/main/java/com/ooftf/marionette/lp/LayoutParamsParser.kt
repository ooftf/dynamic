package com.ooftf.marionette.lp

import android.view.ViewGroup
import com.ooftf.fake.myapplication.UnitParser
import org.json.JSONObject

open class LayoutParamsParser: ILayoutParamsParser {
    override fun apply(lp: ViewGroup.LayoutParams, jsonObj: JSONObject) {
        if(jsonObj.has("width")){
            val width = jsonObj.getString("width")
            if(width == "match_parent"){
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT
            }else if(width == "wrap_content"){
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
            }else{
                lp.width = UnitParser.stringToPx(width)
            }

        }
        if(jsonObj.has("height")){
            val height = jsonObj.getString("height")
            if(height == "match_parent"){
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT
            }else if(height == "wrap_content"){
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }else{
                lp.height = UnitParser.stringToPx(height)
            }

        }
    }
}