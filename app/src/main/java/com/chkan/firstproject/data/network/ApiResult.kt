package com.chkan.firstproject.data.network

import com.chkan.firstproject.data.network.model.ResponseGson
import com.chkan.firstproject.data.network.model.autocomplete.ListPlacesModel
import com.chkan.firstproject.data.network.model.detail.DetailPlaceModel

sealed class ApiResult{
    class Success (val data: ResponseGson) : ApiResult()
    class Error(val e: Exception) : ApiResult()
}

sealed class ApiPlaceResult{
    class Success (val data: ListPlacesModel) : ApiPlaceResult()
    class Error(val e: Exception) : ApiPlaceResult()
}

sealed class ApiDetailResult{
    class Success (val data: DetailPlaceModel) : ApiDetailResult()
    class Error(val e: Exception) : ApiDetailResult()
}


