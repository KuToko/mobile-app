package com.example.kutoko.ui.detailstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity)  {

    var idToko : String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ProdukFragment()
            1 -> fragment = InformasiFragment()

        }

        fragment?.arguments = Bundle().apply {
            putString("idToko", idToko)
        }

        return fragment as Fragment
    }
}