package com.ooftf.marionette.lp

import android.view.ViewGroup
import android.widget.LinearLayout

object LayoutParamsParserManager {
    var map = HashMap<Class<*>, ILayoutParamsParser>()
    init {
        map[LinearLayout.LayoutParams::class.java] = LinearLayoutParamsParser()
        map[ViewGroup.LayoutParams::class.java] = LayoutParamsParser()
        map[ViewGroup.MarginLayoutParams::class.java] = MarginLayoutParamsParser()
    }
    fun findParse(lp:ViewGroup.LayoutParams): ILayoutParamsParser? {
        return map.get(lp.javaClass)?: map[ViewGroup.MarginLayoutParams::class.java]
    }
}