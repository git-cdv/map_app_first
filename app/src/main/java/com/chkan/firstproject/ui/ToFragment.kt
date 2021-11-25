package com.chkan.firstproject.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.PolyUtil
import java.util.*

class ToFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var mapFragment: SupportMapFragment? = null
    private lateinit var mapObject: GoogleMap

    private var _binding: FragmentToBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentToBinding.inflate(inflater, container, false)

        binding.btnTo.setOnClickListener {
            viewModel.getDirections()
        }

        viewModel.apiResult.observe(viewLifecycleOwner, {
            when(it){
                is ApiResult.Success -> {

                    val markerFrom = MarkerOptions()
                        .position(viewModel.latLngStart)
                        .title("Start")
                    val markerTo = MarkerOptions()
                        .position(viewModel.latLngFinish)
                        .title("Finish")

                    mapObject.addMarker(markerFrom)
                    mapObject.addMarker(markerTo)

                    drawDestination(it.data)

                    mapObject.moveCamera(CameraUpdateFactory.newLatLngZoom(viewModel.latLngStart, 15f))

                    val snackbar = Snackbar.make(binding.root, resources.getText(R.string.ok_text), Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(Color.BLACK).setTextColor(Color.WHITE).show()

                }
                is ApiResult.Error -> {
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
                latLngWork.toString()
                val zoomLevel = 15f
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngWork, zoomLevel))

                setMapLongClick(map)//сетим слушатель на лонгклик для маркера
            }
        }

        // R.id.map is a FrameLayout, not a Fragment
        childFragmentManager.beginTransaction().replace(R.id.mapTo, mapFragment!!).commit()

        // Initialize Places
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyBwwNws4hXwcxyQlWFQgRIB3ohpZ1aqsGw")
        }
        val placesClient = Places.createClient(requireContext())

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(com.chkan.firstproject.R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

          // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.NAME, Place.Field.LAT_LNG))

          // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                viewModel.checkFinish(place.latLng)


                mapObject.addMarker(
                    MarkerOptions()
                        .position(place.latLng)
                        .title(getString(R.string.dropped_finish))//тайтл для снипета
                )

                mapObject.moveCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 15f))

                Log.i("MYAPP", "Place: ${place.name}, ${place.latLng}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("MYAPP", "An error occurred: $status")
            }
        })

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

            viewModel.checkFinish(latLng)

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
                    .title(getString(R.string.dropped_finish))//тайтл для снипета
                    .snippet(snippet)//текст для снипета
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}