package com.example.kutoko.ui.tokosaya.beranda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.kutoko.R
import com.example.kutoko.adapter.adapterTokoSaya.ProductTokoSayaAdapter
import com.example.kutoko.databinding.FragmentBerandaTokoSayaBinding
import com.example.kutoko.ui.tokosaya.upload.UploadProductActivity
import com.example.kutoko.util.TokenManager

class BerandaTokoSaya : Fragment() {

    private var _binding: FragmentBerandaTokoSayaBinding? = null
    private val binding get() = _binding!!


    private lateinit var viewModel: BerandaTokoSayaViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBerandaTokoSayaBinding.inflate(inflater, container, false)

        val layoutManager = GridLayoutManager(context,2)
        binding.rvProduk.layoutManager = layoutManager

        viewModel = ViewModelProvider(this)[BerandaTokoSayaViewModel::class.java]
        viewModel.myStore.observe(viewLifecycleOwner){
            val imageUrl = it.avatar
            Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_baseline_image_24).timeout(30_000).into(binding.ivTokoSaya)
            binding.tvNamaToko.text = it.name
            loadProduct(it.id)

        }

        binding.btTambahProduk.setOnClickListener {
            viewModel.myStore.observe(viewLifecycleOwner){ store ->
                val intent = Intent(requireActivity(),UploadProductActivity::class.java)
                intent.putExtra(UploadProductActivity.BUSINESS_ID, store.id)
                startActivity(intent)
                requireActivity().finish()
            }

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadProduct(idStore: String){
        viewModel.fetchProduct(TokenManager.token,idStore)
        viewModel.myProduct.observe(viewLifecycleOwner){
            if (it != null && it.isNotEmpty()){
                val adapter = ProductTokoSayaAdapter(it)
                binding.rvProduk.adapter = adapter
            }else{
                Toast.makeText(requireActivity(),"hehe",Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorResponse.observe(viewLifecycleOwner){
            if (it){
                binding.rvProduk.isVisible = false
                binding.tvProdukKosong.isVisible = true
            }
        }
    }
}