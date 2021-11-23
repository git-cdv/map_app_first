package com.chkan.firstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chkan.firstproject.databinding.ActivityMainBinding
import com.chkan.firstproject.ui.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position==0){tab.text = "Откуда"}
            else {tab.text = "Куда"}
        }.attach()
    }
}