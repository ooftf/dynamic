package com.ooftf.fake.myapplication.lp

import android.view.ViewGroup
import org.json.JSONObject

class LinearLayoutParamsParser: MarginLayoutParamsParser() {

    override fun apply(lp: ViewGroup.LayoutParams, jsonObj: JSONObject) {
        super.apply(lp, jsonObj)
    }
}