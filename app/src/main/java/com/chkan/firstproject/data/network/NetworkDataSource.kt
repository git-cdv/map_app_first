package com.chkan.firstproject.data.network

import com.chkan.firstproject.data.datatype.Result
import com.chkan.firstproject.data.network.exceptions.handleNetworkExceptions
import com.chkan.firstproject.data.network.model.autocomplete.ListPlacesModel
import com.chkan.firstproject.utils.Constans
import java.lang.Exception

class NetworkDataSource {

    suspend fun getListForSuggestion(query: String) : Result<ListPlacesModel> {

        return try {
            val list = Api.retrofitService.getListPlaces(query, Constans.API_KEY_PLACE)
            Result.success(list)
        } catch (e: Exception) {
            Result.error(handleNetworkExceptions(e))
        }
    }

}