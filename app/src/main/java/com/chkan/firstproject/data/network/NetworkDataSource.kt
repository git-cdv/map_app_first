package com.chkan.firstproject.data.network

import android.util.Log
import com.chkan.firstproject.data.datatype.Result
import com.chkan.firstproject.data.network.exceptions.handleNetworkExceptions
import com.chkan.firstproject.data.network.model.ResponseGson
import com.chkan.firstproject.data.network.model.autocomplete.ListPlacesModel
import com.chkan.firstproject.data.network.model.detail.DetailPlaceModel
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

    suspend fun getDetailPlace(place_id: String) : Result<DetailPlaceModel> {

        return try {
            val place = Api.retrofitService.getDetailPlace(place_id = place_id,apiKey = Constans.API_KEY_PLACE)
            Log.d("MYAPP", "NetworkDataSource - place: $place")
            Result.success(place)
        } catch (e: Exception) {
            Result.error(handleNetworkExceptions(e))
        }
    }

    suspend fun getDirection(latLngStart: String, latLngFinish: String) : Result<ResponseGson> {

        return try {
            val rout = Api.retrofitService.getDirection(latLngStart,latLngFinish,Constans.API_KEY_DIST)
            Log.d("MYAPP", "NetworkDataSource - rout: $rout")
            Result.success(rout)
        } catch (e: Exception) {
            Result.error(handleNetworkExceptions(e))
        }
    }


}