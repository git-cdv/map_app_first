package com.chkan.firstproject.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.graphics.Color
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
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
import com.chkan.firstproject.data.network.ApiDetailResult
import com.chkan.firstproject.data.network.ApiPlaceResult
import com.chkan.firstproject.data.network.model.autocomplete.Prediction
import com.chkan.firstproject.databinding.FragmentFromBinding
import com.chkan.firstproject.utils.hideKeyboard
import com.chkan.firstproject.viewmodels.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import java.util.*

class FromFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var mapFragment: SupportMapFragment? = null
    private lateinit var mapObject: GoogleMap

    private var _binding: FragmentFromBinding? = null
    private val binding get() = _binding!!

    var suggestions : MutableList<String> = mutableListOf()
    var listPlaces : List<Prediction> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFromBinding.inflate(inflater, container, false)

        initSearchView(binding.searchFrom)

        viewModel.apiPlaceResult.observe(viewLifecycleOwner, {
            when(it){
                is ApiPlaceResult.Success -> {
                    listPlaces = it.data.listPlaces
                    val set = mutableSetOf<String>()
                    for (item in listPlaces) {
                        set.add(item.name)
                    }
                    suggestions = set.toMutableList()
                }
                is ApiPlaceResult.Error -> {
                    val snackbar = Snackbar.make(binding.root, resources.getText(R.string.error_text), Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(Color.RED).setTextColor(Color.WHITE).show()
                }
            }
        })

        viewModel.apiDetailResult.observe(viewLifecycleOwner, {
            when(it){
                is ApiDetailResult.Success -> {
                    val lat = it.data.result.geometry.location.lat
                    val lng = it.data.result.geometry.location.lng
                    val latLng = LatLng(lat, lng)
                    addStart(latLng)
                    mapObject.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))
                }
                is ApiDetailResult.Error -> {
                    val snackbar = Snackbar.make(binding.root, resources.getText(R.string.error_text), Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(Color.RED).setTextColor(Color.WHITE).show()
                }
            }
        })

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
            mapFragment!!.getMapAsync { map ->
                mapObject = map
                // добавляем маркер по координатам и "фокусируемся" на нем
                val latLngWork = LatLng(47.84303067630826, 35.13851845689717)
                val zoomLevel = 15f
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngWork, zoomLevel))

                setMapLongClick(map)//сетим слушатель на лонгклик для маркера

            }
        }

        childFragmentManager.beginTransaction().replace(R.id.map, mapFragment!!).commit()

        return binding.root
    }

    //создаем маркер при долгом нажатии
    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            addStart(latLng)
        }
    }

    private fun initSearchView(searchView: SearchView) {

        searchView.queryHint = getString(R.string.search)
        searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 3

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(context, R.layout.search_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)

        searchView.suggestionsAdapter = cursorAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {

                if (query != null && query.length>2) {//если строка поиска не пута и имеет больше 2 символов

                    viewModel.getListPlaces(query)

                    Log.d("MYAPP", "onQueryTextChange - ${query.length}")
                }
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                query?.let {
                    Log.d("MYAPP", "suggestions - $suggestions")
                    suggestions.forEachIndexed { index, suggestion ->
                        if (suggestion.contains(query, true))
                            cursor.addRow(arrayOf(index, suggestion))
                    }
                }

                cursorAdapter.changeCursor(cursor)
                return true
            }
        })

        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            @SuppressLint("Range")
            override fun onSuggestionClick(position: Int): Boolean {
                hideKeyboard()
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, false)

                viewModel.getSelectedPlace(selection)

                return true
            }

        })
    }

    private fun addStart(latLng: LatLng) {

        viewModel.checkStart(latLng)

        // A snippet is additional text that's displayed after the title.
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.5f, Long: %2$.5f",
            latLng.latitude,
            latLng.longitude
        )

        mapObject.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(getString(R.string.dropped_start))//тайтл для снипета
                .snippet(snippet)//текст для снипета
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}