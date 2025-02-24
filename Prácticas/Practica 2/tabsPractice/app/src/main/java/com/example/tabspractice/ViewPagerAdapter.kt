package com.example.tabspractice

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int{
        return 3 //Numero de pestañas
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> Tab1Fragment()
            1 -> Tab2Fragment()
            2 -> Tab3Fragment()
            else-> throw IllegalArgumentException("Invalid Position")
        }
    }
}