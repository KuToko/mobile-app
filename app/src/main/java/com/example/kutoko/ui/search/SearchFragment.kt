package com.example.kutoko.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kutoko.R
import com.example.kutoko.adapter.adapterSearchStore.SearchStoreAdapter
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.apiResponse.FindBusinessResponse
import com.example.kutoko.databinding.FragmentSearchBinding
import com.example.kutoko.util.LocationManager
import com.example.kutoko.util.TokenManager
import retrofit2.Call
import retrofit2.Response
import java.util.Timer
import kotlin.concurrent.timerTask

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var timer : Timer

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(context)
        binding.rvSearch.layoutManager = layoutManager

        val token = TokenManager.token
        val lat = LocationManager.lat
        val long = LocationManager.long
        timer = Timer()

        binding.etSearch.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                timer.cancel()
            }
            override fun afterTextChanged(s: Editable?) {
                val q = s.toString()
                timer = Timer()
                timer.schedule(timerTask {searchUser(q, token, lat, long)}, 1000)
            }
        })
        return root
    }

    private fun searchUser(q: String, token: String?, lat: Double, long: Double) {

        val detailClient = ApiConfig.getApiService().findStore("Bearer $token", q, lat, long)
        detailClient.enqueue(object : retrofit2.Callback<FindBusinessResponse> {
            override fun onResponse(call: Call<FindBusinessResponse>, response: Response<FindBusinessResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val foundStore = response.body()!!.data
                    if (foundStore != null && foundStore.isNotEmpty()){
                        val adapter = SearchStoreAdapter(foundStore)
                        binding.rvSearch.adapter = adapter
                    }else{
                        binding.tvSearchEmpty.isVisible = true
                        binding.tvSearchEmpty.text = String.format(getString(R.string.text_tidak_dapat_ditemukan), q)
                        binding.rvSearch.isVisible = false
                        Log.d("Search Toko", "Search Query : $q")
                    }
                } else {
                    binding.tvSearchEmpty.isVisible = true
                    binding.tvSearchEmpty.text = String.format(getString(R.string.text_tidak_dapat_ditemukan), q)
                    binding.rvSearch.isVisible = false
                    Log.e("Search Toko", "onResponseFailure Token : $token")
                    Log.e("Search Toko", "onResponseFailure Response : ${response.raw()}")
                }
            }

            override fun onFailure(call: Call<FindBusinessResponse>, t: Throwable) {
                //_isLoading.value = false
                Log.e("Search Toko", "onEnqueueFailure: ${t.message}")
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}