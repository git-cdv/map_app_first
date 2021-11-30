package com.chkan.firstproject.utils

import android.app.Application
import com.chkan.firstproject.data.local.LocalDataSource

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        localData = LocalDataSource(this)
    }

    companion object {
        lateinit var instance: MyApp
            private set

        lateinit var localData: LocalDataSource
            private set
    }
}