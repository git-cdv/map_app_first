package com.chkan.firstproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import com.chkan.firstproject.R
import com.chkan.firstproject.databinding.FragmentToBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class ToFragment : Fragment() {

    private var mapFragment: SupportMapFragment? = null
    private var _binding: FragmentToBinding? = null
    private val binding get() = _binding!!

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

        }

        //binding.listTo.adapter = userAdapter

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
                // добавляем маркер по координатам и "фокусируемся" на нем
                val latLngWork = LatLng(47.84303067630826, 35.13851845689717)
                val zoomLevel = 15f
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngWork, zoomLevel))

                setMapLongClick(map)//сетим слушатель на лонгклик для маркера
            })
        }

        // R.id.map is a FrameLayout, not a Fragment
        childFragmentManager.beginTransaction().replace(R.id.mapTo, mapFragment!!).commit()

        return binding.root
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