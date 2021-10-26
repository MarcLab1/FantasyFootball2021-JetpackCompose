package com.ff.compose

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val ARG_PAGENO = "pageno"

class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numTabs: Int) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {

        val fragment = BestUnknownTeamFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_PAGENO, position + 1)
        }
        return fragment

    }

    override fun getItemCount(): Int {
        return numTabs
    }
}