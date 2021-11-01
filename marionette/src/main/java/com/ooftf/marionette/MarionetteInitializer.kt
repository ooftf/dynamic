package com.ooftf.marionette

import android.content.Context
import androidx.startup.Initializer
import com.scwang.smart.refresh.footer.ClassicsFooter

import com.scwang.smart.refresh.layout.SmartRefreshLayout

import com.scwang.smart.refresh.header.ClassicsHeader






class MarionetteInitializer:Initializer<String> {
    //static 代码段可以防止内存泄露
    override fun create(context: Context): String {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            //layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white)//全局设置主题颜色
            ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter (context).setDrawableSize(20F)
        }
        return ""
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return emptyList<Class<out Initializer<*>>>().toMutableList()
    }
}