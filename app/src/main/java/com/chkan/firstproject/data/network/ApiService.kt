package com.chkan.firstproject.data.network

import com.chkan.firstproject.data.network.model.ResponseGson
import com.chkan.firstproject.data.network.model.autocomplete.ListPlacesModel
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
}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
