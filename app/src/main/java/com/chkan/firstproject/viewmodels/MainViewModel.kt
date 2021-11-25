package com.chkan.firstproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chkan.firstproject.data.network.Api
import com.chkan.firstproject.data.network.ApiPlaceResult
import com.chkan.firstproject.data.network.ApiResult
import com.chkan.firstproject.utils.Constans
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    lateinit var latLngStart: LatLng
    lateinit var latLngFinish: LatLng

    private val _apiResult = MutableLiveData<ApiResult>()
    val apiResult: LiveData<ApiResult>
        get() = _apiResult

    private val _apiPlaceResult = MutableLiveData<ApiPlaceResult>()
    val apiPlaceResult: LiveData<ApiPlaceResult>
        get() = _apiPlaceResult

    fun getDirections(){
        viewModelScope.launch {
            try {
                val origin = latLngStart.latitude.toString() + "," + latLngStart.longitude.toString()
                val dest = latLngFinish.latitude.toString() + "," + latLngFinish.longitude.toString()
                val response = Api.retrofitService.getDirection(origin,dest,Constans.API_KEY_DIST)
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

    fun getListPlaces(query: String) {
        viewModelScope.launch {
            try {
                val response = Api.retrofitService.getListPlaces(query,Constans.API_KEY_PLACE)
                _apiPlaceResult.value = ApiPlaceResult.Success(response)
                Log.d("MYAPP", "MainViewModel - getListPlaces: ${response.listPlaces}")
            }catch (e: Exception) {
                //_apiResult.value = ApiResult.Error(e)
                Log.d("MYAPP", "MainViewModel - getListPlaces: $e")
            }
        }
    }

}