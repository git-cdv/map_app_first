package com.chkan.firstproject.ui.directions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.chkan.firstproject.R
import com.chkan.base.utils.Result
import com.chkan.base.utils.Constans
import com.chkan.base.utils.toLatLng
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
        val list = viewModel.getListHistory(Constans.WHO_TO)
        val recyclerView: RecyclerView = v.findViewById(R.id.rv_history)
        val adapter = MainAdapter(list)
        recyclerView.adapter = adapter

        adapter.onItemClick = { item ->
            viewModel.updateLatLngSelectedPlaceLiveData(Constans.WHO_TO, Result.success(item.latlng?.toLatLng()))
            Log.d("MYAPP", "FromBottomFragment - onItemClick item: ${item.name}")
            dismiss()
        }

        return v
    }
}