package com.example.kutoko.ui.detailstore

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kutoko.adapter.ListProductAdapter
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.DataItem
import com.example.kutoko.data.ListProductResponse
import com.example.kutoko.databinding.FragmentProdukBinding
import com.example.kutoko.util.TokenManager
import retrofit2.Call
import retrofit2.Response

class ProdukFragment : Fragment() {

    private var _binding: FragmentProdukBinding? = null
    private val binding get() = _binding!!

    private var idToko: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProdukBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            idToko = it.getString("idToko")
        }

        val token = TokenManager.token

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvProduk.layoutManager = layoutManager

        getListProduct(token.toString(), idToko.toString())
    }

    private fun getListProduct(token: String, idToko: String) {
        val detailClient = ApiConfig.getApiService().getListProduct("Bearer $token", idToko)
        detailClient.enqueue(object : retrofit2.Callback<ListProductResponse> {
            override fun onResponse(call: Call<ListProductResponse>, response: Response<ListProductResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val listProduct = response.body()?.data
                    if (listProduct != null && listProduct.isNotEmpty()){
                        val adapter = ListProductAdapter(listProduct)
                        binding.rvProduk.adapter = adapter
                        Log.d("List Product", "Size Product : ${listProduct.size}")
                        Log.d("List Product", "idToko : $idToko")
                    }else{
                        binding.rvProduk.isVisible = false
                        binding.tvProdukKosong.isVisible = true
                        Log.e("List Product", "Size Product : ${listProduct?.size}")
                        Log.e("List Product", "idToko : $idToko")
                    }
                } else {
                    binding.rvProduk.isVisible = false
                    binding.tvProdukKosong.isVisible = true
                    Log.e("List Product", "onResponseFailure Token : $token")
                    Log.e("List Product", "onResponseFailure idToko : $idToko")
                    Log.e("List Product", "onResponseFailure Response : ${response.raw()}")
                }
            }

            override fun onFailure(call: Call<ListProductResponse>, t: Throwable) {
                //_isLoading.value = false
                Log.e("List Product", "onEnqueueFailure: ${t.message}")
            }
        })

    }
}