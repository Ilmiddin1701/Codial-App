@file:Suppress("DEPRECATION")

package com.ilmiddin1701.codial_app.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ilmiddin1701.codial_app.fragments.guruhlar.ItemViewPager2Fragment
import com.ilmiddin1701.codial_app.fragments.guruhlar.ItemViewPagerFragment

@Suppress("DEPRECATION")
class VpAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return if (position == 0){
            ItemViewPagerFragment()
        }else {
            ItemViewPager2Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        val list = arrayOf("Ochilgan guruhlar", "Ochilayotgan guruhlar")
        return list[position]
    }
}