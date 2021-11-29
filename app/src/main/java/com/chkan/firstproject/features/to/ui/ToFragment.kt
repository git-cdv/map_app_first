package com.chkan.firstproject.features.to.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import android.graphics.Color
import android.os.Bundle
import android.provider.BaseColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.activityViewModels
import com.chkan.firstproject.R
import com.chkan.firstproject.data.network.ApiResult
import com.chkan.firstproject.data.network.model.ResponseGson
import com.chkan.firstproject.databinding.FragmentToBinding
import com.chkan.firstproject.features.resultMap.ui.ResultMapActivity
import com.chkan.firstproject.utils.Constans
import com.chkan.firstproject.utils.Constans.LATLNG_FINISH
import com.chkan.firstproject.utils.Constans.LATLNG_START
import com.chkan.firstproject.utils.hideKeyboard
import com.chkan.firstproject.viewmodels.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.PolyUtil


class ToFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var mapFragment: SupportMapFragment? = null
    private lateinit var mapObject: GoogleMap

    private var _binding: FragmentToBinding? = null
    private val binding get() = _binding!!

    var suggestions : MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentToBinding.inflate(inflater, container, false)

        initSearchView(binding.searchTo)

        binding.btnTo.setOnClickListener {

            val intent = Intent(context, ResultMapActivity::class.java).apply {
                val start = "${viewModel.latLngStart.latitude},${viewModel.latLngStart.longitude}"
                val finish = "${viewModel.latLngFinish.latitude},${viewModel.latLngFinish.longitude}"
                putExtra(LATLNG_START, start)
                putExtra(LATLNG_FINISH, finish)
            }
            startActivity(intent)
        }

        viewModel.listForSuggestionLiveData.observe(viewLifecycleOwner, {
            suggestions = it
        })

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
            mapFragment!!.getMapAsync { map ->
                mapObject = map
                // добавляем маркер по координатам и "фокусируемся" на нем
                val latLngWork = LatLng(47.84303067630826, 35.13851845689717)
                latLngWork.toString()
                val zoomLevel = 15f
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngWork, zoomLevel))

                setMapLongClick(map)
            }
        }

        viewModel.latLngSelectedPlaceToLiveData.observe(viewLifecycleOwner, {
                addFinish(it)
                mapObject.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 15F))

        })

        childFragmentManager.beginTransaction().replace(R.id.mapTo, mapFragment!!).commit()

        return binding.root
    }

    //создаем маркер при долгом нажатии
    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            addFinish(latLng)
        }
    }

    private fun addFinish(latLng: LatLng) {
        viewModel.checkFinish(latLng)
        mapObject.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(getString(R.string.dropped_finish))
        )

    }

    private fun initSearchView(searchView: SearchView) {

        searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 3

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(
            context,
            R.layout.search_item,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        searchView.suggestionsAdapter = cursorAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {

                if (query != null && query.length > 2) {//если строка поиска не пута и имеет больше 2 символов
                    viewModel.getListForSuggestion(query)
                }
                val cursor =
                    MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                query?.let {
                    //перебор элементов, предоставляя последовательный индекс с элементом
                    suggestions.forEachIndexed { index, value ->
                        if (value.contains(query, true))//если вводимое значение есть в
                            cursor.addRow(arrayOf(index, value))
                    }
                }

                cursorAdapter.changeCursor(cursor)
                return true
            }
        })

        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            @SuppressLint("Range")
            override fun onSuggestionClick(position: Int): Boolean {
                hideKeyboard()
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection =
                    cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, false)

                viewModel.getLatLngSelectedPlace(Constans.WHO_TO,selection)

                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}