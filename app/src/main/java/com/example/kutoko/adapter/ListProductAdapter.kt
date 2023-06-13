package com.example.kutoko.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kutoko.R
import com.example.kutoko.data.DataItem
import com.example.kutoko.databinding.ItemListProdukBinding

class ListProductAdapter(private val listProduct: List<DataItem>) : RecyclerView.Adapter<ListProductAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(ItemListProdukBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(listProduct[position].productImage)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(holder.image)
        holder.name.text = listProduct[position].name
        holder.desc.text = listProduct[position].description
        holder.desc.text = "Rp. ${listProduct[position].price}"
    }

    override fun getItemCount() = listProduct.size

    class ViewHolder(private val binding: ItemListProdukBinding) : RecyclerView.ViewHolder(binding.root) {
        val image:ImageView = binding.ivProductImg
        val name: TextView = binding.tvProductName
        val desc: TextView = binding.tvProductDesc
        val price: TextView = binding.tvProductPrice
    }
}