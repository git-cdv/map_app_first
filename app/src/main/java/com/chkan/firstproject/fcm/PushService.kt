package com.chkan.firstproject.fcm


import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(TAG, "PushService - onMessageReceived: ${remoteMessage.from}")

        val intent = Intent(INTENT_FILTER)
        remoteMessage.data.forEach { entity ->
            intent.putExtra(entity.key,entity.value)
        }

        sendBroadcast(intent)
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    companion object {
        const val INTENT_FILTER = "PUSH_EVENT"
        const val FCM_KEY_ACTION = "action"
        const val FCM_ACTION_ROUT = "create_rout"
        const val FCM_ACTION_HOME = "way_home"
        const val FCM_START = "notif_start"
        const val FCM_FINISH = "notif_finish"
        const val FCM_HOME = "home_latlng"
        const val TAG = "MYAPP"
    }
}