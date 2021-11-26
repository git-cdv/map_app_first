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
import com.chkan.firstproject.data.network.ApiPlaceResult
import com.chkan.firstproject.data.network.ApiResult
import com.chkan.firstproject.data.network.model.autocomplete.Prediction
import com.chkan.firstproject.features.from.usecase.GetListForSuggestionUseCase
import com.chkan.firstproject.utils.Constans
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    ////change in Hilt
    private val suggestionUseCase = GetListForSuggestionUseCase()

    ////

    lateinit var latLngStart: LatLng
    lateinit var latLngFinish: LatLng
    var listPlaces : List<Prediction> = listOf()

    private val _apiResult = MutableLiveData<ApiResult>()
    val apiResult: LiveData<ApiResult>
        get() = _apiResult

    private val _listForSuggestionLiveData = MutableLiveData<MutableList<String>>()
    val listForSuggestionLiveData: LiveData<MutableList<String>>
        get() = _listForSuggestionLiveData

    private val _apiDetailResult = MutableLiveData<ApiDetailResult>()
    val apiDetailResult: LiveData<ApiDetailResult>
        get() = _apiDetailResult

    private val isErrorMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean>
        get() = isErrorMutableLiveData

    fun getDirections(){
        viewModelScope.launch {
            try {
                val origin = latLngStart.latitude.toString() + "," + latLngStart.longitude.toString()
                val dest = latLngFinish.latitude.toString() + "," + latLngFinish.longitude.toString()
                val response = Api.retrofitService.getDirection(origin,dest,Constans.API_KEY_DIST)
                _apiResult.value = ApiResult.Success(response)
                Log.d("MYAPP", "MainViewModel - apiResult_OK: $response")
            }catch (e: Exception) {
                _apiResult.value = ApiResult.Error(e)
                Log.d("MYAPP", "MainViewModel - getDirections()_ERROR: $e")
            }
        }
    }

    fun checkStart(latLng: LatLng) {
        latLngStart = latLng
    }

    fun checkFinish(latLng: LatLng) {
        latLngFinish = latLng
    }

    fun getListForSuggestion(query: String) {//need return MutableList<String> through LiveData
        viewModelScope.launch {
            val result = suggestionUseCase.getListForSuggestionUseCase(query)
            updateListForSuggestionLiveData(result)
        }
    }

    fun getSelectedPlace(name: String?) {
        val selectedId = listPlaces.find { it.name==name }?.placeId
        Log.d("MYAPP", "getSelectedPlace - selectedId: $selectedId")

        viewModelScope.launch {
            try {
                val response = selectedId?.let { Api.retrofitService.getDetailPlace(place_id = it,apiKey = Constans.API_KEY_PLACE) }
                Log.d("MYAPP", "MainViewModel - getSelectedPlace response: $response")
                _apiDetailResult.value = response?.let { ApiDetailResult.Success(it) }
                if (response != null) {
                    Log.d("MYAPP", "MainViewModel - getDetailPlace: ${response.result.geometry.location.lat}")
                }
            }catch (e: Exception) {
                _apiDetailResult.value = ApiDetailResult.Error(e)
                Log.d("MYAPP", "MainViewModel - getDetailPlace: $e")
            }
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