package com.example.kutoko.adapter.adapterFavorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kutoko.data.Favorite
import com.example.kutoko.databinding.FavoriteItemBinding
import com.example.kutoko.ui.detailstore.DetailStoreActivity

class FavoriteAdapter(private val listFavorite: List<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val favoriteItem = listFavorite[position]
        val imageUrl = favoriteItem.avatar
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.binding.ivFavorite)

        holder.binding.tvNamaToko.text = favoriteItem.name
        holder.binding.tvKategoriToko.text = favoriteItem.categories

        holder.binding.root.setOnClickListener{
            val intent = Intent(holder.binding.root.context, DetailStoreActivity::class.java)
            intent.putExtra("idToko", favoriteItem.Id )
            intent.putExtra(DetailStoreActivity.STORE_PROFILE,Favorite(favoriteItem.Id,favoriteItem.name,favoriteItem.upvotes,favoriteItem.avatar,favoriteItem.categories))
            holder.binding.root.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = listFavorite.size

    class ListViewHolder(var binding: FavoriteItemBinding) : RecyclerView.ViewHolder(binding.root)
}