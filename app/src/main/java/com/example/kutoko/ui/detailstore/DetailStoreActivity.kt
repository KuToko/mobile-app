package com.example.kutoko.ui.detailstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.kutoko.R
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.DetailStoreResponse
import com.example.kutoko.databinding.ActivityDetailStoreBinding
import com.example.kutoko.util.TokenManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Response

class DetailStoreActivity : AppCompatActivity() {

    private lateinit var binding :ActivityDetailStoreBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = TokenManager.token.toString()
        val idToko = intent.getStringExtra("idToko").toString()


        val detailPagerAdapter = DetailPagerAdapter(this)
        detailPagerAdapter.idToko = idToko
        //detailPagerAdapter.username =
        val viewPager: ViewPager2 = binding.vpDetail
        viewPager.adapter = detailPagerAdapter
        val tabs: TabLayout = binding.tabsDetail
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        getDetailtoko(token, idToko)

    }

    private fun getDetailtoko(token: String, idToko: String) {
        val detailClient = ApiConfig.getApiService().getDetailStore("Bearer ${token}", idToko)
        detailClient.enqueue(object : retrofit2.Callback<DetailStoreResponse> {
            override fun onResponse(call: Call<DetailStoreResponse>, response: Response<DetailStoreResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    setDetailToko(response.body())
                } else {
                    Log.e("Detail Toko", "onResponseFailure Token : ${token}")
                    Log.e("Detail Toko", "onResponseFailure Response : ${response.raw()}")
                }
            }

            override fun onFailure(call: Call<DetailStoreResponse>, t: Throwable) {
                //_isLoading.value = false
                Log.e("Detail Toko", "onEnqueueFailure: ${t.message}")
            }
        })
    }

    private fun setDetailToko(it: DetailStoreResponse?) {

        Glide.with(this)
            .load(it?.data?.avatar)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(binding.ivDetailImg)

        binding.tvDetailNama.text = it?.data?.businessName
        binding.detailUpvote.text = String.format(getString(R.string.detailUpvote), it?.data?.upvotes)
    }

}