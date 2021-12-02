package com.chkan.firstproject.data.network

import com.chkan.firstproject.data.datatype.Result
import com.chkan.firstproject.data.network.exceptions.handleNetworkExceptions
import com.chkan.firstproject.data.network.model.ResponseGson
import com.chkan.firstproject.data.network.model.autocomplete.ListPlacesModel
import com.chkan.firstproject.data.network.model.detail.DetailPlaceModel
import com.chkan.firstproject.data.network.model.geocode.GeocodeModel
import com.chkan.firstproject.utils.Constans
import java.lang.Exception
import javax.inject.Inject

class NetworkDataSource @Inject constructor() {

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
            Result.success(place)
        } catch (e: Exception) {
            Result.error(handleNetworkExceptions(e))
        }
    }

    suspend fun getDirection(latLngStart: String, latLngFinish: String) : Result<ResponseGson> {

        return try {
            val rout = Api.retrofitService.getDirection(latLngStart,latLngFinish,Constans.API_KEY_DIST)
            Result.success(rout)
        } catch (e: Exception) {
            Result.error(handleNetworkExceptions(e))
        }
    }

    suspend fun getNameFromGeocoding(latLng: String) : Result<GeocodeModel> {
        return try {
            val model = Api.retrofitService.getNameFromGeocode(latlng = latLng,apiKey = Constans.API_KEY_PLACE )
            Result.success(model)
        } catch (e: Exception) {
            Result.error(handleNetworkExceptions(e))
        }

    }


}