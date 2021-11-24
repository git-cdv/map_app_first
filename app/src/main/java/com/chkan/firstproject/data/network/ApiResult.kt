package com.chkan.firstproject.data.network

import com.chkan.firstproject.data.network.model.ResponseGson

sealed class ApiResult{
    class Success (val data: ResponseGson) : ApiResult()
    class Error(val e: Exception) : ApiResult()
}
