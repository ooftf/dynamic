package com.ooftf.demo.dynamic

import android.content.Context
import android.content.res.AssetManager
import android.text.TextUtils
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream

object MockUtils {
    fun getJSONData(context: Context, fileName: String): JSONObject? {
        val bytes = getAssertsFile(context, fileName) ?: return null
        val json = String(bytes)
        return if (TextUtils.isEmpty(json)) {
            null
        } else JSONObject(json)
    }

    fun getAssertsFile(context: Context, fileName: String?): ByteArray? {
        var inputStream: InputStream? = null
        val assetManager = context.assets
        try {
            inputStream = assetManager.open(fileName!!)
            if (inputStream == null) {
                return null
            }
            var bis: BufferedInputStream? = null
            val length: Int
            try {
                bis = BufferedInputStream(inputStream)
                length = bis.available()
                val data = ByteArray(length)
                bis.read(data)
                return data
            } catch (e: IOException) {
            } finally {
                if (bis != null) {
                    try {
                        bis.close()
                    } catch (e: Exception) {
                    }
                }
            }
            return null
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}