package com.ooftf.demo.dynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.ooftf.marionette.node.NodeContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val root = findViewById<LinearLayout>(R.id.root)
        val nodeContext = NodeContext(this)
        // 这里才是获取数据部分
        val data = MockUtils.getJSONData(this, "home.json")
        data?.apply {
            val nodeRootView = nodeContext.parse(root,data)
            root.addView(nodeRootView)
        }
        nodeContext.addLoadMoreListener {
            lifecycleScope.launch {
                delay(2000)
                it.finishLoadMore()
            }
        }
        nodeContext.addRefreshListener {
            lifecycleScope.launch {
                delay(2000)
                it.finishRefresh()
            }
        }
    }
}