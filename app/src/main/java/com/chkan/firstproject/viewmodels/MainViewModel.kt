package com.chkan.firstproject.viewmodels

import androidx.lifecycle.*
import com.chkan.base.utils.ResultType
import com.chkan.base.utils.Result
import com.chkan.base.utils.Constans
import com.chkan.base.utils.toLatLng
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

    var listLocalModelFrom = listOf<LocalModelUI>()
    var listLocalModelTo = listOf<LocalModelUI>()

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
        return getListForSuggestionUseCase.getListForSuggestionUseCase(query)
    }

    fun getLatLngSelectedPlace(who:Int, name: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getLatLngSelectedPlaceUseCase.getLatLngSelectedPlace(name)
            if(who== Constans.WHO_FROM) nameStart = name.toString() else nameFinish = name.toString()
            updateLatLngSelectedPlaceLiveData(who, result)
        }
    }

    fun updateLatLngSelectedPlaceLiveData(who: Int, result: Result<LatLng>) {
        if (result.resultType==ResultType.SUCCESS && result.data != null) {
            if(who== Constans.WHO_FROM){
                _latLngSelectedPlaceFromLiveData.postValue(result.data!!)
            } else {
                _latLngSelectedPlaceToLiveData.postValue(result.data!!)
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

    fun getListHistory(who: Int): Array<String> {
        if(who== Constans.WHO_FROM) {
            listLocalModelFrom = saveSelectedPlaceUseCase.getFromHistory(who)
            listLocalModelFrom.reversed()
            return listLocalModelFrom.map {
                it.name
            }.toTypedArray()
        } else {
            listLocalModelTo = saveSelectedPlaceUseCase.getFromHistory(who)
            listLocalModelTo.reversed()
            return listLocalModelTo.map {
                it.name
            }.toTypedArray()
        }
    }


}