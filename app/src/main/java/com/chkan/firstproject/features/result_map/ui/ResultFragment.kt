package com.chkan.firstproject.features.result_map.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chkan.firstproject.R
import com.chkan.firstproject.databinding.FragmentMainBinding
import com.chkan.firstproject.databinding.FragmentResultBinding
import com.chkan.firstproject.features.result_map.viewmodel.ResultViewModel
import com.chkan.firstproject.utils.Constans
import com.chkan.firstproject.utils.toLatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private var mapFragment: SupportMapFragment? = null
    private lateinit var map: GoogleMap
    private val viewModel: ResultViewModel by viewModels()

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        val resultModel = ResultFragmentArgs.fromBundle(requireArguments()).resultModel

        Log.d("MYAPP", "ResultFragment - resultModel: $resultModel")

        /*val latLngStart = intent.getStringExtra(Constans.LATLNG_START)
        val latLngFinish = intent.getStringExtra(Constans.LATLNG_FINISH)
        val nameStart = intent.getStringExtra(Constans.NAME_START)
        val nameFinish = intent.getStringExtra(Constans.NAME_FINISH)

        if (latLngStart!=null&&latLngFinish!=null){
            viewModel.getRout(latLngStart,latLngFinish)
            if (nameStart!=null&&nameFinish!=null) {
                viewModel.saveLatLng(nameStart,latLngStart,nameFinish,latLngFinish)
            }
        }

        viewModel.polylineLiveData.observe(this, {

            if (latLngStart!=null&&latLngFinish!=null) {

                val start = latLngStart.toLatLng()

                val markerFrom = MarkerOptions()
                    .position(start)
                    .title("Start")
                val markerTo = MarkerOptions()
                    .position(latLngFinish.toLatLng())
                    .title("Finish")

                map.addMarker(markerFrom)
                map.addMarker(markerTo)

                map.addPolyline(it)

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 15f))

                val snackbar = Snackbar.make(
                    binding.root,
                    resources.getText(R.string.ok_text),
                    Snackbar.LENGTH_LONG
                )
                snackbar.setBackgroundTint(Color.BLACK).setTextColor(Color.WHITE).show()
            }
        })

        viewModel.isErrorLiveData.observe(this, {
            if(it) showError()
        })
*/

            mapFragment = SupportMapFragment.newInstance()
            mapFragment?.getMapAsync(OnMapReadyCallback { googleMap ->
                map = googleMap
                //enableMyLocation()
                val latLng = LatLng(1.289545, 103.849972)
                googleMap.addMarker(
                    MarkerOptions().position(latLng)
                        .title("Singapore")
                )
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            })

        childFragmentManager.beginTransaction().replace(R.id.mapResult, mapFragment!!).commit()

        val toolbar : MaterialToolbar = binding.resultToolbar
        toolbar.setNavigationOnClickListener {findNavController().navigateUp()}

        return binding.root
    }




    /*private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(//Если разрешение нет - запрашиваем
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            return
        } else {
            //Если разрешение предоставлено включаем слой местоположения
            map.isMyLocationEnabled = true
        }
    }

    //слушаем ответ на запрос разрешения
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }else {
            //здесь что-то делаем если не дает разрешение
        }
    }*/

    private fun showError() {
        val snackbar = Snackbar.make(
            binding.root,
            resources.getText(R.string.error_text),
            Snackbar.LENGTH_LONG
        )
        snackbar.setBackgroundTint(Color.RED).setTextColor(Color.WHITE).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}