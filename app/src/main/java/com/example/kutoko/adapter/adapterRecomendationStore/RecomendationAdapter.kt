package com.example.kutoko.adapter.adapterRecomendationStore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kutoko.R
import com.example.kutoko.data.database.ListRecommendationItem
import com.example.kutoko.databinding.RecomendationItemBinding

class RecomendationAdapter : PagingDataAdapter<ListRecommendationItem,RecomendationAdapter.MyViewHolder> (
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecomendationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }


    class MyViewHolder(private val binding: RecomendationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: ListRecommendationItem) {
            val imageUrl = data.avatar
            val distanceinM = if (data.distance_in_m < 20) {
                20.0
            }else{
                data.distance_in_m
            }
            Glide.with(itemView.context).load(imageUrl).placeholder(R.drawable.ic_baseline_image_24).into(binding.ivTokoTerdekat)
            binding.tvNamaToko.text = data.name
            binding.tvJarakToko.text = if (data.distance_in_km < 1) {
                distanceinM.toInt().toString() + "m"
            }else{
                data.distance_in_km.toInt().toString() + "km"
            }
            binding.tvKategoriToko.text = data.categories
            binding.tvTotalReview.text = data.upvotes.toString()

//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
//                intent.putExtra(DetailStoryActivity.USER_STORY, data)
//                itemView.context.startActivity(intent)
//            }
        }
    }



    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListRecommendationItem>() {
            override fun areItemsTheSame(oldItem: ListRecommendationItem, newItem: ListRecommendationItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListRecommendationItem, newItem: ListRecommendationItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}