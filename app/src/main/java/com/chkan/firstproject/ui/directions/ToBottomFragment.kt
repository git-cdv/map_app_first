package com.chkan.firstproject.ui.directions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.chkan.firstproject.R
import com.chkan.base.utils.Result
import com.chkan.base.utils.Constans
import com.chkan.base.utils.toLatLng
import com.chkan.firstproject.ui.directions.adapter.MainAdapter
import com.chkan.firstproject.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ToBottomFragment : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val adapter by lazy {MainAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.modal_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list = viewModel.getListHistory(Constans.WHO_TO)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_history)
        adapter.setList(list)
        recyclerView.adapter = adapter

        adapter.onItemClick = { item ->
            viewModel.updateLatLngSelectedPlaceLiveData(Constans.WHO_TO, Result.success(item.latlng?.toLatLng()))
            dismiss()
        }
    }
}