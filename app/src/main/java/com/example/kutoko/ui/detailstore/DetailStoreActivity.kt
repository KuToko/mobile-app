package com.example.kutoko.ui.detailstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.kutoko.R
import com.example.kutoko.adapter.adapterSimiliar.SimiliarAdapter
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.apiResponse.DetailStoreResponse
import com.example.kutoko.data.Favorite
import com.example.kutoko.data.apiResponse.SimiliarBusinessResponse
import com.example.kutoko.data.database.favoriteDatabase.ListFavoriteItem
import com.example.kutoko.databinding.ActivityDetailStoreBinding
import com.example.kutoko.ui.favorite.viewmodel.FavoriteViewModelFactory
import com.example.kutoko.ui.favorite.viewmodel.MainFavoriteViewModel
import com.example.kutoko.util.FavoriteManager
import com.example.kutoko.util.LocationManager
import com.example.kutoko.util.TokenManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Response

class DetailStoreActivity : AppCompatActivity() {

    private lateinit var binding :ActivityDetailStoreBinding
    private lateinit var mainFavoriteViewModel: MainFavoriteViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val STORE_PROFILE = "STORE_PROFILE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = TokenManager.token.toString()
        val idToko = intent.getStringExtra("idToko").toString()
        val lat = LocationManager.lat
        val long = LocationManager.long

        title = "Detail UMKM"

        mainFavoriteViewModel = obtainMainViewModel(this@DetailStoreActivity)
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


        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSimiliarBusiness.layoutManager = layoutManager

        getSimiliar(token, idToko, lat, long)

        @Suppress("DEPRECATION")
        val store = intent.getParcelableExtra<Favorite>(STORE_PROFILE)
        if (store != null) {
            mainFavoriteViewModel.getAllFavorite().observe(this) {
                if (it != null) {
                    checkFavorite(it,store.Id)
                }
            }
        }

        binding.btAddFavorite.setOnClickListener {

            if (store != null) {
                val favorite = ListFavoriteItem(0,store.Id,store.name,store.avatar,store.upvotes,store.categories)

                if (!FavoriteManager.isAdded) {
                    mainFavoriteViewModel.insertFavorite(favorite)
                    binding.btAddFavorite.setImageResource(R.drawable.solidheart)
                }else{
                    mainFavoriteViewModel.deleteFavorite(store.Id)
                    binding.btAddFavorite.setImageResource(R.drawable.stripeheart)
                }
            }

        }
    }

    private fun getSimiliar(token: String, idToko: String, lat: Double, long: Double) {

        val detailClient = ApiConfig.getApiService().getSimiliarStore("Bearer $token", idToko, lat, long)
        detailClient.enqueue(object : retrofit2.Callback<SimiliarBusinessResponse> {
            override fun onResponse(call: Call<SimiliarBusinessResponse>, response: Response<SimiliarBusinessResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val similiarBusiness = response.body()!!.data
                    if (similiarBusiness != null && similiarBusiness.isNotEmpty()){
                        val adapter = SimiliarAdapter(similiarBusiness)
                        binding.linearSimiliarBusiness.isVisible = true
                        binding.rvSimiliarBusiness.adapter = adapter
                    }else{
                        binding.linearSimiliarBusiness.isVisible = false
                        Log.e("Similiar Store", "Size Similiar Store : ${similiarBusiness?.size}")
                        Log.e("Similiar Store", "idToko : $idToko")
                    }
                } else {
                    Log.e("Detail Toko", "onResponseFailure Token : $token")
                    Log.e("Detail Toko", "onResponseFailure Response : ${response.raw()}")
                }
            }

            override fun onFailure(call: Call<SimiliarBusinessResponse>, t: Throwable) {
                //_isLoading.value = false
                Log.e("Detail Toko", "onEnqueueFailure: ${t.message}")
            }
        })
    }


    private fun obtainMainViewModel(activity: AppCompatActivity): MainFavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory)[MainFavoriteViewModel::class.java]

    }

    private fun checkFavorite(listFavorite: List<ListFavoriteItem>,idStore : String) {
        val containStore = listFavorite.any { it.id?.contains(idStore, ignoreCase = true) ?: false }

        if (containStore) {
            binding.btAddFavorite.setImageResource(R.drawable.solidheart)
            FavoriteManager.isAdded = true
        } else {
            binding.btAddFavorite.setImageResource(R.drawable.stripeheart)
            FavoriteManager.isAdded = false
        }

    }


    private fun getDetailtoko(token: String, idToko: String) {
        val detailClient = ApiConfig.getApiService().getDetailStore("Bearer $token", idToko)
        detailClient.enqueue(object : retrofit2.Callback<DetailStoreResponse> {
            override fun onResponse(call: Call<DetailStoreResponse>, response: Response<DetailStoreResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    setDetailToko(response.body())
                } else {
                    Log.e("Detail Toko", "onResponseFailure Token : $token")
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