package com.example.kutoko.adapter.adapterTokoSaya

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kutoko.R
import com.example.kutoko.data.apiResponse.DataItem
import com.example.kutoko.databinding.MyProductItemBinding

class ProductTokoSayaAdapter(private val listProduct: List<DataItem>) : RecyclerView.Adapter<ProductTokoSayaAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productItem = listProduct[position]
        Glide.with(holder.itemView)
            .load(productItem.productImage)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(holder.binding.ivProductImg)

        holder.binding.tvNamaProduct.text = productItem.name
        holder.binding.tvHargaProduct.text = "Rp. ${productItem.price}"
        holder.binding.btEdit.setOnClickListener {

        }

    }

    override fun getItemCount(): Int = listProduct.size


    class ViewHolder(var binding: MyProductItemBinding) : RecyclerView.ViewHolder(binding.root)
}