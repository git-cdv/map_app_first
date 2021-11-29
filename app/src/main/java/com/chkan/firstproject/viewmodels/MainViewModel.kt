package com.chkan.firstproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chkan.firstproject.data.datatype.Result
import com.chkan.firstproject.data.datatype.ResultType
import com.chkan.firstproject.data.network.Api
import com.chkan.firstproject.data.network.ApiDetailResult
import com.chkan.firstproject.data.network.ApiResult
import com.chkan.firstproject.data.network.model.autocomplete.Prediction
import com.chkan.firstproject.features.from.usecase.GetLatLngSelectedPlaceUseCase
import com.chkan.firstproject.features.from.usecase.GetListForSuggestionUseCase
import com.chkan.firstproject.utils.Constans
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    ////change in Hilt
    private val getListForSuggestionUseCase = GetListForSuggestionUseCase()
    private val getLatLngSelectedPlaceUseCase = GetLatLngSelectedPlaceUseCase()
    ////

    lateinit var latLngStart: LatLng
    lateinit var latLngFinish: LatLng

    private val _listForSuggestionLiveData = MutableLiveData<MutableList<String>>()
    val listForSuggestionLiveData: LiveData<MutableList<String>>
        get() = _listForSuggestionLiveData

    private val _latLngSelectedPlaceFromLiveData = MutableLiveData<LatLng>()
    val latLngSelectedPlaceFromLiveData: LiveData<LatLng>
        get() = _latLngSelectedPlaceFromLiveData

    private val _latLngSelectedPlaceToLiveData = MutableLiveData<LatLng>()
    val latLngSelectedPlaceToLiveData: LiveData<LatLng>
        get() = _latLngSelectedPlaceToLiveData

    private val isErrorMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean>
        get() = isErrorMutableLiveData

    fun checkStart(latLng: LatLng) {
        latLngStart = latLng
    }

    fun checkFinish(latLng: LatLng) {
        latLngFinish = latLng
    }

    fun getListForSuggestion(query: String) {
        viewModelScope.launch {
            val result = getListForSuggestionUseCase.getListForSuggestionUseCase(query)
            updateListForSuggestionLiveData(result)
        }
    }

    fun getLatLngSelectedPlace(who:Int, name: String?) {
        viewModelScope.launch {
            val result = getLatLngSelectedPlaceUseCase.getLatLngSelectedPlace(name)
            updateLatLngSelectedPlaceLiveData(who, result)
        }
    }

    private fun updateLatLngSelectedPlaceLiveData(who: Int, result: Result<LatLng>) {
        if (result.resultType==ResultType.SUCCESS) {
            if(who==Constans.WHO_FROM){
                _latLngSelectedPlaceFromLiveData.value = result.data!!
            } else {
                _latLngSelectedPlaceToLiveData.value = result.data!!
            }
            // TODO: Handle case with NULL
        } else {
            onResultError()
        }
    }

    private fun updateListForSuggestionLiveData(result: Result<MutableList<String>>) {
        if (result.resultType==ResultType.SUCCESS) {
            _listForSuggestionLiveData.value = result.data!!
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


}