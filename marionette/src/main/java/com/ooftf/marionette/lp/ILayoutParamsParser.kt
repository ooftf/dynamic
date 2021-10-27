package com.ooftf.fake.myapplication.lp

import android.view.ViewGroup
import org.json.JSONObject

interface ILayoutParamsParser {
    fun apply(lp:ViewGroup.LayoutParams, jsonObj:JSONObject)
}