package com.github.dormesica.webviewtest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.dormesica.mapcontroller.layers.VectorLayer
import com.github.dormesica.webviewtest.R

class VectorLayerAdapter(list: List<VectorLayer>): RecyclerView.Adapter<VectorLayerAdapter.ViewHolder>() {

    var listener: ((VectorLayer) -> Unit)? = null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_layer_name)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tv_layer_description)
        private val isShownCheckBox: CheckBox = itemView.findViewById(R.id.cb_is_layer_showing)

        init {
            itemView.setOnClickListener {
                listener?.invoke(layersList[adapterPosition])
            }
        }

        fun bind(layer: VectorLayer) {
           nameTextView.text = layer.name
           descriptionTextView.text = layer.description
           isShownCheckBox.isChecked = layer.isVisible
        }
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
        holder.bind(layersList[position])
    }

    fun setOnItemClickListener(listener: (VectorLayer) -> Unit) {
        this.listener = listener
    }
}
