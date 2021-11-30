package com.chkan.firstproject.features.resultMap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chkan.firstproject.data.datatype.Result
import com.chkan.firstproject.data.datatype.ResultType
import com.chkan.firstproject.features.from.usecase.SaveSelectedPlaceUseCase
import com.chkan.firstproject.features.resultMap.usecase.GetPolylineForRouteUseCase
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResultViewModel : ViewModel(){

    ////change in Hilt
    private val getPolylineForRouteUseCase = GetPolylineForRouteUseCase()
    private val saveSelectedPlaceUseCase = SaveSelectedPlaceUseCase()

    private val _polylineLiveData = MutableLiveData<PolylineOptions>()
    val polylineLiveData: LiveData<PolylineOptions>
        get() = _polylineLiveData

    private val isErrorMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean>
        get() = isErrorMutableLiveData

    fun getRout(latLngStart: String, latLngFinish: String) {
        viewModelScope.launch {
            val result = getPolylineForRouteUseCase.getPolylineForRoute(latLngStart,latLngFinish)
            updatePolylineLiveData(result)
        }
    }

    private fun updatePolylineLiveData(result: Result<PolylineOptions>) {
        if (result.resultType== ResultType.SUCCESS) {
            _polylineLiveData.value = result.data!!
            // TODO: Handle case with NULL
        } else {
            onResultError()
        }
    }

    private fun onResultError() {
        viewModelScope.launch {
            delay(300)
            // TODO: Handle case with Loading
            //isLoadingLiveData(false)
        }.invokeOnCompletion {
            isErrorMutableLiveData.value = true
            // TODO: Create notification in UI
        }
    }

    fun saveLatLng(nameStart: String, latLngStart: String, nameFinish: String, latLngFinish: String) {
        saveSelectedPlaceUseCase.savePlace(nameStart,latLngStart,nameFinish,latLngFinish)
    }

}