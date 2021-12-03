package com.chkan.data.sources.network.exceptions

import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.UnknownHostException

fun handleNetworkExceptions(ex: Exception): Exception {
    return when (ex) {
        is IOException -> NetworkConnectionException()
        is UnknownHostException -> NetworkConnectionException()
        is HttpException -> apiErrorFromCodeException(ex.code())
        else -> GenericNetworkException()
    }
}

private fun apiErrorFromCodeException(code: Int): Exception {
    return if (code == 400) {
        BadRequestException()
    } else {
        GenericNetworkException()
    }
}