package com.chkan.firstproject.viewmodels

import androidx.lifecycle.*
import com.chkan.base.utils.*
import com.chkan.domain.models.LocalModelUI
import com.chkan.domain.usecases.directions.GetLatLngSelectedPlaceUseCase
import com.chkan.domain.usecases.directions.GetListForSuggestionUseCase
import com.chkan.domain.usecases.result_map.SaveSelectedPlaceUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getListForSuggestionUseCase : GetListForSuggestionUseCase,
    private val getLatLngSelectedPlaceUseCase : GetLatLngSelectedPlaceUseCase,
    private val saveSelectedPlaceUseCase : SaveSelectedPlaceUseCase
): ViewModel(){

    var latLngStart: LatLng = "47.8723852,35.3004297".toLatLng()
    var latLngFinish: LatLng = "50.4501,30.5234".toLatLng()
    var nameStart: String = ""
    var nameFinish: String = ""

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

    private val _historyLiveData: MutableLiveData <List<LocalModelUI>> = MutableLiveData()
    val historyLiveData: LiveData <List<LocalModelUI>>
        get() = _historyLiveData

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
        return getListForSuggestionUseCase.getListForSuggestionUseCase(query)
    }

    fun getLatLngSelectedPlace(who:Int, name: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getLatLngSelectedPlaceUseCase.getLatLngSelectedPlace(name)
            if(who== WHO_FROM) nameStart = name.toString() else nameFinish = name.toString()
            updateLatLngSelectedPlaceLiveData(who, result)
        }
    }

    fun updateLatLngSelectedPlaceLiveData(who: Int, result: Result<LatLng>) {
        if (result.resultType==ResultType.SUCCESS) {
            if (result.data != null) {
                if (who == WHO_FROM) {
                    _latLngSelectedPlaceFromLiveData.postValue(result.data!!)//выше проверяю на null, но всеравно ругается
                } else {
                    _latLngSelectedPlaceToLiveData.postValue(result.data!!) //выше проверяю на null, но всеравно ругается
                }
            }
        } else {
            onResultError()
        }
    }

    private fun onResultError() {
        viewModelScope.launch {
            delay(300)
        }.invokeOnCompletion {
            _isErrorLiveData.value = true
        }
    }

    fun getListHistory(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = saveSelectedPlaceUseCase.getFromHistory(type).reversed()
            _historyLiveData.postValue(result)
        }
    }


}