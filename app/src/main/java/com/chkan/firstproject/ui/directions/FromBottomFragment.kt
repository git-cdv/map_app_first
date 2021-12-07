package com.chkan.firstproject.ui.directions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chkan.base.utils.Result
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.chkan.base.utils.WHO_FROM
import com.chkan.firstproject.R
import com.chkan.base.utils.toLatLng
import com.chkan.firstproject.ui.directions.adapter.MainAdapter
import com.chkan.firstproject.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FromBottomFragment : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val adapter by lazy {MainAdapter { item ->
        viewModel.updateLatLngSelectedPlaceLiveData(
            WHO_FROM,
            Result.success(item.latlng?.toLatLng())
        )
        dismiss()
    }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.modal_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list = viewModel.getListHistory(WHO_FROM)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_history)
        adapter.setList(list)
        recyclerView.adapter = adapter
    }
}