package com.chkan.firstproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.chkan.base.utils.TAG_SHEET_FROM
import com.chkan.base.utils.TAG_SHEET_TO
import com.chkan.base.utils.WHO_FROM
import com.chkan.base.utils.toStringModel
import com.chkan.domain.models.ResultModel
import com.chkan.firstproject.databinding.ActivityMainBinding
import com.chkan.firstproject.fcm.PushService.Companion.FCM_ACTION_HOME
import com.chkan.firstproject.fcm.PushService.Companion.FCM_ACTION_ROUT
import com.chkan.firstproject.fcm.PushService.Companion.FCM_FINISH
import com.chkan.firstproject.fcm.PushService.Companion.FCM_HOME
import com.chkan.firstproject.fcm.PushService.Companion.FCM_KEY_ACTION
import com.chkan.firstproject.fcm.PushService.Companion.FCM_START
import com.chkan.firstproject.fcm.PushService.Companion.INTENT_FILTER
import com.chkan.firstproject.ui.directions.FromBottomFragment
import com.chkan.firstproject.ui.directions.ToBottomFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    //message from remote

    lateinit var binding: ActivityMainBinding
    private var back_pressed: Long = 0
    private var navController: NavController? = null

    private lateinit var pushBroadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        navController = host.navController

        initPushBroadcast()

        intent.extras?.keySet()?.firstOrNull { it== FCM_KEY_ACTION }?.let {
            var bundle = bundleOf()
            when(intent.extras?.getString(FCM_KEY_ACTION)){
                FCM_ACTION_ROUT -> {
                    val start = intent.extras?.getString(FCM_START)
                    val finish = intent.extras?.getString(FCM_FINISH)
                    Log.d(TAG, "Intent: start - $start, finish - $finish")

                    val result = ResultModel(
                        startName = "PUSH Start",
                        startLatNng = start!!,
                        finishName = "PUSH Finish",
                        finishLatNng = finish!!
                    )
                    bundle = bundleOf("from_push_rout" to result)
                }
                FCM_ACTION_HOME -> {
                    val home = intent.extras?.getString(FCM_HOME)
                    Log.d(TAG, "Intent: home - $home")

                    bundle = bundleOf("from_push_home" to home)
                }
            }

            navController!!.navigate(R.id.resultFragment,bundle)
        }


    }

    private fun initPushBroadcast() {
        pushBroadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val extras = intent?.extras
                //firstOrNull вернёт null, если элемент не будет найден
                extras?.keySet()?.firstOrNull { it== FCM_KEY_ACTION }?.let {
                    val start = extras.getString(FCM_START)
                    val finish = extras.getString(FCM_FINISH)
                    Log.d(TAG, "BroadcastReceiver: start - $start, finish - $finish")
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(INTENT_FILTER)

        registerReceiver(pushBroadcastReceiver,intentFilter)
    }

    fun getBottomSheet(who:Int) {
        if (who == WHO_FROM) {
            FromBottomFragment().apply {
                show(supportFragmentManager,TAG_SHEET_FROM)
            }
        } else {
            ToBottomFragment().apply {
                show(supportFragmentManager, TAG_SHEET_TO)
            }
        }
    }

    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed() else Toast.makeText(
            baseContext, resources.getString(R.string.text_back),
            Toast.LENGTH_SHORT
        ).show()
        back_pressed = System.currentTimeMillis()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(pushBroadcastReceiver)
    }

    companion object {
        private const val TAG = "MYAPP"
    }

}
