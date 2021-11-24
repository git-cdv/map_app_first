package com.chkan.firstproject.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.chkan.firstproject.R
import com.chkan.firstproject.data.network.ApiResult
import com.chkan.firstproject.data.network.model.ResponseGson
import com.chkan.firstproject.databinding.FragmentToBinding
import com.chkan.firstproject.viewmodels.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.PolyUtil
import java.util.*

class ToFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var mapFragment: SupportMapFragment? = null
    private lateinit var mapObject: GoogleMap
    private var _binding: FragmentToBinding? = null
    private val binding get() = _binding!!

    private val latLngOrig = LatLng(47.84451, 35.12993)
    private val latLngDest = LatLng(47.83875, 35.14028)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentToBinding.inflate(inflater, container, false)

        val user = arrayOf("Abhay","Joseph","Maria","Avni","Apoorva","Chris","David","Kaira","Dwayne","Christopher",
            "Jim","Russel","Donald","Brack","Vladimir")

        val userAdapter : ArrayAdapter<String> = ArrayAdapter(
            context!!,
            android.R.layout.simple_list_item_1,
            user
        )

        binding.btnTo.setOnClickListener {
            val origin = latLngOrig.latitude.toString() + "," + latLngOrig.longitude.toString()
            val destination = latLngDest.latitude.toString() + "," + latLngDest.longitude.toString()

            viewModel.getDirections(destination, origin,"AIzaSyAXrI3OF_DmXo-r6V_klQE_3mPEiZ4lIlo")
        }

        viewModel.apiResult.observe(viewLifecycleOwner, {
            when(it){
                is ApiResult.Success -> {

                    val markerFrom = MarkerOptions()
                        .position(latLngOrig)
                        .title("Start")
                    val markerTo = MarkerOptions()
                        .position(latLngDest)
                        .title("Finish")

                    mapObject.addMarker(markerFrom)
                    mapObject.addMarker(markerTo)
                    mapObject.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngDest, 11.6f))

                    drawDestination(it.data)
                }
                is ApiResult.Error -> {
                    val snackbar = Snackbar.make(binding.root, resources.getText(R.string.error_text), Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(Color.RED).setTextColor(Color.WHITE).show()
                }
            }
        })


        binding.searchTo.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchTo.clearFocus()
                if (user.contains(query)){

                    userAdapter.filter.filter(query)

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter.filter(newText)
                return false
            }

        })

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
            mapFragment!!.getMapAsync(OnMapReadyCallback { map ->
                mapObject = map
                // добавляем маркер по координатам и "фокусируемся" на нем
                val latLngWork = LatLng(47.84303067630826, 35.13851845689717)
                latLngWork.toString()
                val zoomLevel = 15f
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngWork, zoomLevel))

                setMapLongClick(map)//сетим слушатель на лонгклик для маркера
            })
        }

        // R.id.map is a FrameLayout, not a Fragment
        childFragmentManager.beginTransaction().replace(R.id.mapTo, mapFragment!!).commit()

        return binding.root
    }

    private fun drawDestination(data: ResponseGson) {
        val shape = data.routes.get(0).overviewPolyline.points
        val polyline = PolylineOptions()
            .addAll(PolyUtil.decode(shape))
            .width(8f)
            .color(Color.BLACK)
        mapObject.addPolyline(polyline)
    }

    //создаем маркер при долгом нажатии
    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->

            // A snippet is additional text that's displayed after the title.
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )

            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))//тайтл для снипета
                    .snippet(snippet)//текст для снипета
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}