package com.chkan.data.sources.network

import android.util.Log
import com.chkan.data.BuildConfig
import com.chkan.base.utils.Result
import com.chkan.data.sources.network.exceptions.handleNetworkExceptions
import com.chkan.data.sources.network.model.autocomplete.ListPlacesModel
import com.chkan.data.sources.network.model.detail.DetailPlaceModel
import com.chkan.data.sources.network.model.geocode.GeocodeModel
import com.chkan.data.sources.network.model.rout.ResponseGson
import java.lang.Exception
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val routApi: RoutService, private val placeApi: PlaceService) {

    suspend fun getListForSuggestion(query: String) : Result<ListPlacesModel> {
        return try {
            val list = placeApi.getListPlaces(query, BuildConfig.API_KEY_PLACE)
            Log.d("MYAPP", "NetworkDataSource - getListForSuggestion OK: ${list.listPlaces}")
            Result.success(list)
        } catch (e: Exception) {
            Log.d("MYAPP", "NetworkDataSource - getListForSuggestion ERROR: ${e.message}")
            Result.error(handleNetworkExceptions(e))
        }
    }

    suspend fun getDetailPlace(place_id: String) : Result<DetailPlaceModel> {
        return try {
            val place = placeApi.getDetailPlace(place_id = place_id,apiKey = BuildConfig.API_KEY_PLACE)
            Log.d("MYAPP", "NetworkDataSource - getDetailPlace OK: ${place.result.geometry}")
            Result.success(place)
        } catch (e: Exception) {
            Log.d("MYAPP", "NetworkDataSource - getDetailPlace ERROR: ${e.message}")
            Result.error(handleNetworkExceptions(e))
        }
    }

    suspend fun getDirection(latLngStart: String, latLngFinish: String) : Result<ResponseGson> {
        return try {
            val rout = routApi.getDirection(latLngStart,latLngFinish,BuildConfig.API_KEY_DIST)
            Log.d("MYAPP", "NetworkDataSource - getDirection OK: ${rout.status}")
            Result.success(rout)
        } catch (e: Exception) {
            Log.d("MYAPP", "NetworkDataSource - getDirection ERROR: ${e.message}")
            Result.error(handleNetworkExceptions(e))
        }
    }

    suspend fun getNameFromGeocoding(latLng: String) : Result<GeocodeModel> {
        return try {
            val model = placeApi.getNameFromGeocode(latlng = latLng,apiKey = BuildConfig.API_KEY_PLACE )
            Log.d("MYAPP", "NetworkDataSource - getNameFromGeocoding OK: ${model.results}")
            Result.success(model)
        } catch (e: Exception) {
            Log.d("MYAPP", "NetworkDataSource - getNameFromGeocoding ERROR: ${e.message}")
            Result.error(handleNetworkExceptions(e))
        }

    }


}