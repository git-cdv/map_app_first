package com.chkan.firstproject.data.network

import com.chkan.firstproject.data.network.model.ResponseApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://maps.googleapis.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @GET("maps/api/directions/json")
    suspend fun getDirection(@Query("destination") destination: String,
                             @Query("origin") origin: String,
                     @Query("key") apiKey: String): ResponseApi
}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}

//https://maps.googleapis.com/maps/api/directions/json?destination=47.84232,35.13641&origin=47.83522,35.14759&key=AIzaSyAXrI3OF_DmXo-r6V_klQE_3mPEiZ4lIlo