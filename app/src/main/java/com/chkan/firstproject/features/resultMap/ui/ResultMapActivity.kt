package com.chkan.firstproject.features.resultMap.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.chkan.firstproject.R
import com.chkan.firstproject.databinding.ActivityResultMapBinding
import com.chkan.firstproject.features.resultMap.viewmodel.ResultViewModel
import com.chkan.firstproject.utils.Constans
import com.chkan.firstproject.utils.toLatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class ResultMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityResultMapBinding
    private val viewModel: ResultViewModel by viewModels()


    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val latLngStart = intent.getStringExtra(Constans.LATLNG_START)
        val latLngFinish = intent.getStringExtra(Constans.LATLNG_FINISH)

        if (latLngStart!=null&&latLngFinish!=null){viewModel.getRout(latLngStart,latLngFinish)}

        viewModel.polylineLiveData.observe(this, {

            if (latLngStart!=null&&latLngFinish!=null) {

                val start = latLngStart.toLatLng(latLngStart)

                val markerFrom = MarkerOptions()
                    .position(start)
                    .title("Start")
                val markerTo = MarkerOptions()
                    .position(latLngFinish.toLatLng(latLngFinish))
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

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapResult) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val toolbar : MaterialToolbar = findViewById(R.id.result_toolbar)
        toolbar.setNavigationOnClickListener {finish()}

    }

    override fun onMapReady(googleMap: GoogleMap) { //коллбек после загрузки карты
        map = googleMap
        enableMyLocation()
    }

    private fun enableMyLocation() {
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
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
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
    }

}