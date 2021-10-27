package com.ooftf.demo.dynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.ooftf.marionette.nr.NodeRenderContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val root = findViewById<LinearLayout>(R.id.root)
        val nodeContext = NodeRenderContext(this)
        // 这里才是获取数据部分
        val data = MockUtils.getJSONData(this, "home.json")
        data?.apply {
            val nodeRootView = nodeContext.parse(root,data.getJSONObject("view"))
            root.addView(nodeRootView)
        }
    }
}