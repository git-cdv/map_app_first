package com.chkan.firstproject.features

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chkan.firstproject.R
import com.chkan.firstproject.databinding.FragmentMainBinding
import com.chkan.firstproject.features.from.ui.FromFragment
import com.chkan.firstproject.features.to.ui.ToFragment
import com.google.android.material.tabs.TabLayoutMediator

private var _binding: FragmentMainBinding? = null
private val binding get() = _binding!!

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        viewPager.isUserInputEnabled = false
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position==0){tab.text = resources.getString(R.string.text_from)}
            else {tab.text = resources.getString(R.string.text_to)}
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FromFragment()
            else -> ToFragment()
        }
    }
}