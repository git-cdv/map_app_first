package com.chkan.firstproject.features.to.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.activityViewModels
import com.chkan.firstproject.R
import com.chkan.firstproject.data.datatype.Result
import com.chkan.firstproject.utils.Constans
import com.chkan.firstproject.utils.toLatLng
import com.chkan.firstproject.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ToBottomFragment : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.modal_bottom_sheet, container, false)

        val values = viewModel.getListHistory(Constans.WHO_TO)

        val listView = v.findViewById<ListView>(R.id.list_view)

        val adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, values) }

        listView.adapter =adapter

        listView.setOnItemClickListener{ _, _, position, _ ->
            val item = viewModel.listLocalModelTo[position]
            Log.d("MYAPP", "mListView - position: $position, item : $item")
            viewModel.updateLatLngSelectedPlaceLiveData(Constans.WHO_TO, Result.success(item.latlng?.toLatLng()))
            dismiss()
        }

        return v
    }
}