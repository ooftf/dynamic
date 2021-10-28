package com.ooftf.marionette.node

import android.util.Log

class Binder {
    val map = HashMap<String,(String)->Unit>()
    fun update(key:String,value:String):Boolean{
        Log.e("update","${key} :: ${value} :: ${map}")
        val action = map[key]
        return if(action != null){
            action.invoke(value)
            true
        }else{
            false
        }

    }

    fun put(key:String,action:(String)->Unit){
        map.put(key,action)
    }
}