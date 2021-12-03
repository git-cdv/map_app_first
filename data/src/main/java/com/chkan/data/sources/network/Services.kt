package com.chkan.data.sources.network


import com.chkan.data.sources.network.model.autocomplete.ListPlacesModel
import com.chkan.data.sources.network.model.detail.DetailPlaceModel
import com.chkan.data.sources.network.model.geocode.GeocodeModel
import com.chkan.data.sources.network.model.rout.ResponseGson
import retrofit2.http.GET
import retrofit2.http.Query


interface RoutService {

    @GET(API_ADD_URL_ROUT)
    suspend fun getDirection(@Query("destination") destination: String,
                             @Query("origin") origin: String,
                             @Query("key") apiKey: String): ResponseGson
    companion object {
        const val API_ADD_URL_ROUT = "maps/api/directions/json"
    }
}

interface PlaceService {

    @GET(API_ADD_URL_AUTOCOMPLETE)
    suspend fun getListPlaces(@Query("input") input: String,
                              @Query("key") apiKey: String): ListPlacesModel

    @GET(API_ADD_URL_DETAIL)
    suspend fun getDetailPlace(@Query("place_id") place_id: String,
                               @Query("fields") fields: String="geometry/location",
                               @Query("key") apiKey: String): DetailPlaceModel

    @GET(API_ADD_URL_GEOCODE)
    suspend fun getNameFromGeocode(@Query("latlng") latlng: String,
                                   @Query("location_type") location_type: String="ROOFTOP",
                                   @Query("key") apiKey: String): GeocodeModel

    companion object {
        const val API_ADD_URL_AUTOCOMPLETE = "maps/api/place/autocomplete/json"
        const val API_ADD_URL_DETAIL = "maps/api/place/details/json"
        const val API_ADD_URL_GEOCODE = "maps/api/geocode/json"
    }
}



