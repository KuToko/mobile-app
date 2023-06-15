package com.example.kutoko.adapter.adapterSimiliar

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kutoko.R
import com.example.kutoko.data.apiResponse.DataSimiliarBusiness
import com.example.kutoko.databinding.RecomendationItemBinding
import com.example.kutoko.ui.detailstore.DetailStoreActivity

class SimiliarAdapter(private val listSimiliar: List<DataSimiliarBusiness>) : RecyclerView.Adapter<SimiliarAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(RecomendationItemBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(listSimiliar[position].avatar)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(holder.image)
        holder.name.text = listSimiliar[position].name
        holder.jarak.text = if (listSimiliar[position].distanceInKm < 1) {
            listSimiliar[position].distanceInM.toInt().toString() + "m"
        }else{
            listSimiliar[position].distanceInKm.toInt().toString() + "km"
        }
        //holder.kategori.text = listSimiliar[position].categories
        holder.review.text = listSimiliar[position].upvotes.toString()

        holder.itemView.setOnClickListener {
            val moveIntent = Intent(holder.itemView.context, DetailStoreActivity::class.java)
            moveIntent.putExtra("idToko", listSimiliar[position].id )
            holder.itemView.context.startActivity(moveIntent)
        }
    }

    override fun getItemCount() = listSimiliar.size

    class ViewHolder(private val binding: RecomendationItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = binding.ivTokoTerdekat
        val name: TextView = binding.tvNamaToko
        val jarak: TextView = binding.tvJarakToko
        val kategori: TextView = binding.tvKategoriToko
        val review:TextView = binding.tvTotalReview
    }
}