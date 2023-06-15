package com.example.kutoko.ui.userLocation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kutoko.adapter.adapterLocation.LocationAdapter
import com.example.kutoko.data.Location
import com.example.kutoko.data.database.locationDatabase.LocationUser
import com.example.kutoko.databinding.ActivityLocationListBinding
import com.example.kutoko.ui.userLocation.locationViewModel.LocationViewModelFactory
import com.example.kutoko.ui.userLocation.locationViewModel.MainLocationViewModel
import com.example.kutoko.util.LocationManager

class LocationList : AppCompatActivity() {
    private lateinit var binding : ActivityLocationListBinding
    private lateinit var mainLocationViewModel: MainLocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvAlamatSekarang.text = LocationManager.addressLocation

        val manager = LinearLayoutManager(this)
        binding.rvLokasiLain.layoutManager = manager
        binding.rvLokasiLain.setHasFixedSize(true)

        supportActionBar?.title = "Lokasi Kamu"

        mainLocationViewModel = obtainMainViewModel(this@LocationList)

        mainLocationViewModel.getLocation(false).observe(this) {
            setUserData(it)
        }

        binding.btTambahLokasi.setOnClickListener {
            finish()
            startActivity(Intent(this,AddLocation::class.java))

        }
    }

    private fun obtainMainViewModel(activity: AppCompatActivity): MainLocationViewModel {
        val factory = LocationViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory)[MainLocationViewModel::class.java]
    }

    private fun setUserData(listLocation : List<LocationUser>) {
        val locationUser = ArrayList<Location>()
        listLocation.map {
            val location = Location(lat = it.lat,lon = it.lon, address = it.address, isUsed = it.isUsed)
            locationUser.add(location)
        }

        if (listLocation.isNotEmpty()){
            val adapter = LocationAdapter(locationUser)
            binding.rvLokasiLain.adapter = adapter
        }
    }
}