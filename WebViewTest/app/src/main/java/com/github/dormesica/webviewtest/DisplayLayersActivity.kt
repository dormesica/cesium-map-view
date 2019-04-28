package com.github.dormesica.webviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dormesica.mapcontroller.layers.VectorLayer
import com.github.dormesica.webviewtest.adapters.VectorLayerAdapter

class DisplayLayersActivity : AppCompatActivity() {

    private lateinit var recycleView: RecyclerView
    private lateinit var layersAdapter: VectorLayerAdapter
    private lateinit var viewManager: LinearLayoutManager

    companion object {
        const val EXTRA_LAYER_LIST = "com.github.dormesica.mapcontoller.DisplayLayersActivity.EXTRA_LATER_LIST"

        private const val TAG = "DisplayLayersActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_layers)

        val layers = intent.getParcelableArrayListExtra<VectorLayer>(EXTRA_LAYER_LIST)

        viewManager = LinearLayoutManager(this)
        layersAdapter = VectorLayerAdapter(layers)
        layersAdapter.setOnItemClickListener {
            Log.d(TAG, " ${it.name} layer clicked")
        }
        recycleView = findViewById<RecyclerView>(R.id.rv_layers).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = layersAdapter
        }
    }
}
