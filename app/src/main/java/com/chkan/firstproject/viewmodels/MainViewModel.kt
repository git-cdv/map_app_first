package com.chkan.firstproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chkan.firstproject.data.network.Api
import com.chkan.firstproject.data.network.ApiResult
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    private val _apiResult = MutableLiveData<ApiResult>()
    val apiResult: LiveData<ApiResult>
        get() = _apiResult

    fun getDirections(origin:String, destination:String,key:String){
        viewModelScope.launch {
            try {
                val response = Api.retrofitService.getDirection(origin,destination,key)
                _apiResult.value = ApiResult.Success(response)
            }catch (e: Exception) {
                _apiResult.value = ApiResult.Error(e)
            }
        }
    }

}