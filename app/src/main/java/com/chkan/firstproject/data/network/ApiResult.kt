package com.chkan.firstproject.data.network

import com.chkan.firstproject.data.network.model.ResponseApi

sealed class ApiResult{
    class Success (val data: ResponseApi) : ApiResult()
    class Error(val e: Exception) : ApiResult()
}
