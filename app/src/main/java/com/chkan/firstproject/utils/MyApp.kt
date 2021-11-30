package com.chkan.firstproject.utils

import android.app.Application
import com.chkan.firstproject.data.local.LocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MyApp: Application() {

    val applicationScopeIO = CoroutineScope(SupervisorJob() + Dispatchers.IO)

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