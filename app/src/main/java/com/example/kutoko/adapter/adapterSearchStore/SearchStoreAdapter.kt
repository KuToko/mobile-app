package com.example.kutoko.adapter.adapterSearchStore

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kutoko.R
import com.example.kutoko.data.apiResponse.DataSearchItem
import com.example.kutoko.databinding.ItemListProdukBinding
import com.example.kutoko.databinding.MenuItemBinding
import com.example.kutoko.ui.detailstore.DetailStoreActivity

class SearchStoreAdapter(private val listProduct: List<DataSearchItem>) : RecyclerView.Adapter<SearchStoreAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(MenuItemBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(listProduct[position].avatar)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(holder.image)
        holder.name.text = listProduct[position].name
        holder.jarak.text = if (listProduct[position].distanceInKm < 1) {
            listProduct[position].distanceInM.toInt().toString() + "m"
        }else{
            listProduct[position].distanceInKm.toInt().toString() + "km"
        }
        //holder.kategori.text = listProduct[position].categories
        holder.rating.text = listProduct[position].name

        holder.itemView.setOnClickListener {
            val moveIntent = Intent(holder.itemView.context, DetailStoreActivity::class.java)
            moveIntent.putExtra("idToko", listProduct[position].id )
            holder.itemView.context.startActivity(moveIntent)
        }
    }

    override fun getItemCount() = listProduct.size

    class ViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = binding.ivTokoTerdekat
        val name: TextView = binding.tvNamaToko
        val jarak: TextView = binding.tvJarakToko
        val kategori: TextView = binding.tvKategoriToko
        val rating:TextView = binding.tvTotalReview
    }
}