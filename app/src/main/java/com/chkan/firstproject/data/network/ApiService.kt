package com.chkan.firstproject.data.network

import com.chkan.firstproject.data.network.model.ResponseGson
import com.chkan.firstproject.data.network.model.autocomplete.ListPlacesModel
import com.chkan.firstproject.data.network.model.detail.DetailPlaceModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://maps.googleapis.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @GET("maps/api/directions/json")
    suspend fun getDirection(@Query("destination") destination: String,
                             @Query("origin") origin: String,
                     @Query("key") apiKey: String): ResponseGson

    @GET("maps/api/place/autocomplete/json")
    suspend fun getListPlaces(@Query("input") input: String,
                             @Query("key") apiKey: String): ListPlacesModel

    @GET("maps/api/place/details/json")
    suspend fun getDetailPlace(@Query("place_id") place_id: String,
                               @Query("fields") fields: String="geometry/location",
                               @Query("key") apiKey: String): DetailPlaceModel
}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
