package com.chkan.firstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.chkan.firstproject.databinding.ActivityMainBinding
import com.chkan.firstproject.features.from.ui.FromBottomFragment
import com.chkan.firstproject.features.to.ui.ToBottomFragment
import com.chkan.firstproject.utils.Constans
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var back_pressed: Long = 0
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        navController = host.navController
    }

    fun getBottomSheet(who:Int) {
        if (who == Constans.WHO_FROM) {
            FromBottomFragment().apply {
                show(supportFragmentManager, Constans.TAG_SHEET_FROM)
            }
        } else {
            ToBottomFragment().apply {
                show(supportFragmentManager, Constans.TAG_SHEET_TO)
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

}