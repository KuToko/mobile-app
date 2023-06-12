package com.example.kutoko.ui.userLocation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kutoko.MainActivity
import com.example.kutoko.databinding.ActivityFetchUserLocationBinding
import com.example.kutoko.ui.beranda.BerandaViewModel
import com.example.kutoko.ui.beranda.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class FetchUserLocation : AppCompatActivity(){

    private lateinit var binding : ActivityFetchUserLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var berandaViewModel: BerandaViewModel
    private var lat : Double = 0.0
    private var long : Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchUserLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        berandaViewModel = ViewModelProvider(this)[BerandaViewModel::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.loadingSplash.progress = 0
        getMyLocation()
        showProgress()
    }

    private fun showProgress() {
        Handler(Looper.getMainLooper()).postDelayed({
            val progress = binding.loadingSplash.progress + 12
            binding.loadingSplash.progress = progress

            if (progress < 100) {
                showProgress()
            } else {
                berandaViewModel.setLatLng(lat, long)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        },400)
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    long = location.longitude
                    lat = location.latitude
                } else {
                    Toast.makeText(
                        this,
                        "Lokasi Anda Tidak Dapat Ditemukan !!. Default Lokasi Diterapkan !",
                        Toast.LENGTH_SHORT
                    ).show()
                    lat = -7.775241177136506
                    long = 110.393442675452
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ).toString()
            )
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

}