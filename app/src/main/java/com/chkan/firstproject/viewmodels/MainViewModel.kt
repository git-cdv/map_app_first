package com.chkan.firstproject.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.chkan.firstproject.data.datatype.Result
import com.chkan.firstproject.data.datatype.ResultType
import com.chkan.firstproject.features.from.usecase.GetLatLngSelectedPlaceUseCase
import com.chkan.firstproject.features.from.usecase.GetListForSuggestionUseCase
import com.chkan.firstproject.features.from.usecase.SaveSelectedPlaceUseCase
import com.chkan.firstproject.utils.Constans
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    ////change in Hilt
    private val getListForSuggestionUseCase = GetListForSuggestionUseCase()
    private val getLatLngSelectedPlaceUseCase = GetLatLngSelectedPlaceUseCase()
    private val saveSelectedPlaceUseCase = SaveSelectedPlaceUseCase()
    ////

    lateinit var latLngStart: LatLng
    lateinit var latLngFinish: LatLng

    private val queryFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            queryFlow
                .sample(500L)
                .mapLatest(::getSuggestion)
                .collect { result -> _searchSuggestion.value = result}
        }
    }

    fun onNewQuery(query: String) {
        queryFlow.value = query
    }

    private val _searchSuggestion = MutableStateFlow <Result<MutableList<String>>>(Result.empty())
    val searchSuggestion: LiveData<Result<MutableList<String>>>
        get() = _searchSuggestion
            .asLiveData(viewModelScope.coroutineContext)

    private val _latLngSelectedPlaceFromLiveData = MutableLiveData<LatLng>()
    val latLngSelectedPlaceFromLiveData: LiveData<LatLng>
        get() = _latLngSelectedPlaceFromLiveData

    private val _latLngSelectedPlaceToLiveData = MutableLiveData<LatLng>()
    val latLngSelectedPlaceToLiveData: LiveData<LatLng>
        get() = _latLngSelectedPlaceToLiveData

    private val _isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean>
        get() = _isErrorLiveData

    fun checkStart(latLng: LatLng) {
        latLngStart = latLng
    }

    fun checkFinish(latLng: LatLng) {
        latLngFinish = latLng
    }

    private suspend fun getSuggestion(query: String): Result<MutableList<String>> {
        Log.d("MYAPP", "MainViewModel - getSuggestion: query $query")
        return getListForSuggestionUseCase.getListForSuggestionUseCase(query)
    }

    fun getLatLngSelectedPlace(who:Int, name: String?) {
        viewModelScope.launch {
            val result = getLatLngSelectedPlaceUseCase.getLatLngSelectedPlace(name)
            updateLatLngSelectedPlaceLiveData(who, result)
            saveSelectedPlaceUseCase.savePlaceFromSearch(who,name,result.data)
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

    private fun onResultError() {
        viewModelScope.launch {
            delay(300)
            // TODO: Handle case with Loading
            //isLoadingLiveData(false)
        }.invokeOnCompletion {
            _isErrorLiveData.value = true
            // TODO: Create notification in UI
        }
    }


}