package com.chkan.firstproject.data.network

import com.chkan.firstproject.BuildConfig
import com.chkan.firstproject.data.network.model.ResponseGson
import com.chkan.firstproject.data.network.model.autocomplete.ListPlacesModel
import com.chkan.firstproject.data.network.model.detail.DetailPlaceModel
import com.chkan.firstproject.data.network.model.geocode.GeocodeModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BuildConfig.API_BASE_URL)
    .build()

interface ApiService {

    @GET(BuildConfig.API_ADD_URL_ROUT)
    suspend fun getDirection(@Query("destination") destination: String,
                             @Query("origin") origin: String,
                     @Query("key") apiKey: String): ResponseGson

    @GET(BuildConfig.API_ADD_URL_AUTOCOMPLETE)
    suspend fun getListPlaces(@Query("input") input: String,
                             @Query("key") apiKey: String): ListPlacesModel

    @GET(BuildConfig.API_ADD_URL_DETAIL)
    suspend fun getDetailPlace(@Query("place_id") place_id: String,
                               @Query("fields") fields: String="geometry/location",
                               @Query("key") apiKey: String): DetailPlaceModel

    @GET(BuildConfig.API_ADD_URL_GEOCODE)
    suspend fun getNameFromGeocode(@Query("latlng") latlng: String,
                               @Query("location_type") location_type: String="ROOFTOP",
                               @Query("key") apiKey: String): GeocodeModel
}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
