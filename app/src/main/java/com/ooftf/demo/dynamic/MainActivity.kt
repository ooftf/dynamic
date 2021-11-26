package com.ooftf.demo.dynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.ooftf.marionette.node.INode
import com.ooftf.marionette.node.NodeContext
import com.ooftf.marionette.node.recycler_view.RecyclerViewNode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val root = findViewById<LinearLayout>(R.id.root)
        val nodeContext = NodeContext(this)
        // 这里才是获取数据部分
        val data = MockUtils.getJSONData(this, "home.json")
        data?.apply {
            val nodeRootView = nodeContext.parse(root, data)
            root.addView(nodeRootView)
        }
        nodeContext.addLoadMoreListener {


            lifecycleScope.launch {
                delay(2000)
                (it as? RecyclerViewNode)?.let {
                    val child = it.data?.getJSONArray("children")
                    child?.put(
                        JSONObject(                    "            {\n" +
                                "              \"templateId\": \"2313\",\n" +
                                "              \"field\": {\n" +
                                "                \"title\": \"第自定义个\"\n" +
                                "              }\n" +
                                "            }")
                    )
                    (it.getTargetView() as? RecyclerView)?.let {

                        it.adapter?.notifyItemInserted(child?.length()!! -1)
                    }

                }
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