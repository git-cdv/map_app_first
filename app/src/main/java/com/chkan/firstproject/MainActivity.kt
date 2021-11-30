package com.chkan.firstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.chkan.firstproject.databinding.ActivityMainBinding
import com.chkan.firstproject.features.from.ui.FromBottomFragment
import com.chkan.firstproject.features.to.ui.ToBottomFragment
import com.chkan.firstproject.utils.Constans
import com.chkan.firstproject.utils.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var back_pressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        viewPager.isUserInputEnabled = false
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position==0){tab.text = "Откуда"}
            else {tab.text = "Куда"}
        }.attach()
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
            baseContext, "Для выхода нажмите \"назад\" еще раз",
            Toast.LENGTH_SHORT
        ).show()
        back_pressed = System.currentTimeMillis()
    }

}