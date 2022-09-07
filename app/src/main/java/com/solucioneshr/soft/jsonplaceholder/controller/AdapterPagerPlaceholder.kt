package com.solucioneshr.soft.jsonplaceholder.controller

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.solucioneshr.soft.jsonplaceholder.R
import com.solucioneshr.soft.jsonplaceholder.ui.main.ContactFragment
import com.solucioneshr.soft.jsonplaceholder.ui.main.PlaceholderFragment

class AdapterPagerPlaceholder(fm:FragmentManager, private val context: Context): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 ->{
                PlaceholderFragment()
            }
            1 ->{
                ContactFragment()
            }
            else ->{
                PlaceholderFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return context.getString(R.string.tab_text_1)
            }
            1 -> {
                return context.getString(R.string.tab_text_2)
            }
        }
        return super.getPageTitle(position)
    }

}