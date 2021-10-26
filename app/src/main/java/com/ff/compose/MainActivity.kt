package com.ff.compose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout : TabLayout
    private lateinit var tabViewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var root : View =  getLayoutInflater().inflate(R.layout.activity_main, null);
        tabLayout = root.findViewById(R.id.tabLayout)
        setContentView(root)

        val numTabs : Int = 17
        val adapter : TabsPagerAdapter = TabsPagerAdapter(supportFragmentManager, lifecycle, numTabs)
        tabViewPager2 = root.findViewById(R.id.tabViewPager2)
        tabViewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, tabViewPager2) { tab, position ->
            var str: String = "WEEK "
            tab.text = str + (position+1)

        }.attach()

    }

}