package com.chkan.firstproject

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application(){

    override fun onCreate() {
        super.onCreate()

        //TODO: Нужно ли запускать только 1 раз при первом запуске?
        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("MYAPP", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d("MYAPP", "TOKEN : $token")
        })
    }
}