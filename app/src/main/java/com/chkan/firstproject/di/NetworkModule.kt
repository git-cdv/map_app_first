package com.chkan.firstproject.di

import com.chkan.firstproject.BuildConfig
import com.chkan.firstproject.data.network.PlaceService
import com.chkan.firstproject.data.network.RoutService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesBaseUrl() : String = BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL : String) : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideRoutService(retrofit : Retrofit) : RoutService = retrofit.create(RoutService::class.java)

    @Provides
    @Singleton
    fun providePlaceService(retrofit : Retrofit) : PlaceService = retrofit.create(PlaceService::class.java)

}