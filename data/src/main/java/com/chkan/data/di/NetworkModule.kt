package com.chkan.data.di

import com.chkan.data.BuildConfig
import com.chkan.data.sources.network.PlaceService
import com.chkan.data.sources.network.RoutService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesBaseUrl() : String = BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL : String) : Retrofit = Retrofit.Builder()
        .addConverterFactory(Json{
            isLenient = true
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideRoutService(retrofit : Retrofit) : RoutService = retrofit.create(RoutService::class.java)

    @Provides
    @Singleton
    fun providePlaceService(retrofit : Retrofit) : PlaceService = retrofit.create(PlaceService::class.java)

}