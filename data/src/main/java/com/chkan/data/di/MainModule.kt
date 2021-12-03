package com.chkan.data.di

import android.app.Application
import com.chkan.data.sources.local.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideLocalSource(app:Application) : LocalDataSource{ return LocalDataSource(app)}

}