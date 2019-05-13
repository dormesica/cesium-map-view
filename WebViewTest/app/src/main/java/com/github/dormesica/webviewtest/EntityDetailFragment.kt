package com.github.dormesica.webviewtest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.github.dormesica.mapcontroller.layers.Entity
import com.google.android.material.button.MaterialButton
import java.lang.ClassCastException
import java.lang.Exception

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ENTITY = "entity"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EntityDetailFragment.OnEntityStyleEditedListener] interface
 * to handle interaction events.
 * Use the [EntityDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EntityDetailFragment : Fragment() {

    private lateinit var mEntity: Entity
    private lateinit var mListener: OnEntityStyleEditedListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            mListener = context as OnEntityStyleEditedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnEntityStyleEditedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var entity: Entity? = null
        arguments?.let {
            entity = it.getParcelable(ARG_ENTITY)
        }

        if (entity == null) {
            throw Exception()
        }
        mEntity = entity as Entity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_entity_detail, container, false)
        view.findViewById<TextView>(R.id.tv_entity_name).text = mEntity.name
        view.findViewById<TextView>(R.id.tv_entity_description).text = mEntity.description

        view.findViewById<MaterialButton>(R.id.bt_edit).setOnClickListener {
            Log.d("EntityDetailFragment", "Edit clicked") // TODO start edit activity
        }

        return view
    }

    interface OnEntityStyleEditedListener {
        fun onEntityStyleChanged(editor: Entity.Editor)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param entity Parameter 1.
         * @return A new instance of fragment EntityDetailFragment.
         */
        @JvmStatic
        fun newInstance(entity: Entity) =
            EntityDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ENTITY, entity)
                }
            }
    }
}
