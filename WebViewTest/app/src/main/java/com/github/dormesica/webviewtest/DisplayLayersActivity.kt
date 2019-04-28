package com.github.dormesica.webviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dormesica.mapcontroller.layers.VectorLayer

class DisplayLayersActivity : AppCompatActivity() {

    private lateinit var recycleView: RecyclerView
    private lateinit var layersAdapter: RecyclerView.Adapter<*>
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
        layersAdapter = LayersAdapter(layers)
        recycleView = findViewById<RecyclerView>(R.id.rv_layers).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = layersAdapter
        }
    }

    class LayersAdapter(list: List<VectorLayer>): RecyclerView.Adapter<LayersAdapter.ViewHolder>() {
        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.tv_layer_name)
            val descriptionTextView: TextView = itemView.findViewById(R.id.tv_layer_description)
            val isShownCheckBox: CheckBox = itemView.findViewById(R.id.cb_is_layer_showing)
        }

        private val layersList: List<VectorLayer> = list

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layer_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return layersList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.nameTextView.text = layersList[position].name
            holder.descriptionTextView.text = layersList[position].description
            holder.isShownCheckBox.isChecked = layersList[position].isVisible
        }
    }
}
