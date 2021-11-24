package com.chkan.firstproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chkan.firstproject.data.network.Api
import com.chkan.firstproject.data.network.ApiResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    lateinit var latLngStart: LatLng
    lateinit var latLngFinish: LatLng
    private val key = "AIzaSyAXrI3OF_DmXo-r6V_klQE_3mPEiZ4lIlo"

    private val _apiResult = MutableLiveData<ApiResult>()
    val apiResult: LiveData<ApiResult>
        get() = _apiResult

    fun getDirections(){
        viewModelScope.launch {
            try {
                val origin = latLngStart.latitude.toString() + "," + latLngStart.longitude.toString()
                val dest = latLngFinish.latitude.toString() + "," + latLngFinish.longitude.toString()
                val response = Api.retrofitService.getDirection(origin,dest,key)
                _apiResult.value = ApiResult.Success(response)
                Log.d("MYAPP", "MainViewModel - apiResult: $response")
            }catch (e: Exception) {
                _apiResult.value = ApiResult.Error(e)
                Log.d("MYAPP", "MainViewModel - apiResult: $e")
            }
        }
    }

    fun checkStart(latLng: LatLng) {
        latLngStart = latLng
    }

    fun checkFinish(latLng: LatLng) {
        latLngFinish = latLng
    }

}