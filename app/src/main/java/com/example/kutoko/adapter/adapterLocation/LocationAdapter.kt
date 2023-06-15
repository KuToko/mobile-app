package com.example.kutoko.adapter.adapterLocation

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kutoko.MainActivity
import com.example.kutoko.data.Location
import com.example.kutoko.databinding.LocationItemBinding
import com.example.kutoko.util.LocationManager

class LocationAdapter(private val listLocation: List<Location>) : RecyclerView.Adapter<LocationAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = LocationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val locationItem = listLocation[position]

        holder.binding.tvAlamatSekarang.text = locationItem.address

        holder.binding.btJadikanUtama.setOnClickListener{
            LocationManager.lat = locationItem.lat
            LocationManager.long = locationItem.lon
            LocationManager.addressLocation = locationItem.address
            val intent = Intent(holder.binding.root.context, MainActivity::class.java)
            holder.binding.root.context.startActivity(intent)

            val context = holder.binding.root.context

            if (context is Activity){
                context.finish()
            }
        }
    }

    override fun getItemCount(): Int = listLocation.size

    class ListViewHolder(var binding: LocationItemBinding) : RecyclerView.ViewHolder(binding.root)
}