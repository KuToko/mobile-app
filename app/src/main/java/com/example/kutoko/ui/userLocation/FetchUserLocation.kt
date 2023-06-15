package com.example.kutoko.ui.userLocation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kutoko.MainActivity
import com.example.kutoko.databinding.ActivityFetchUserLocationBinding
import com.example.kutoko.ui.beranda.BerandaViewModel
import com.example.kutoko.util.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.*

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

        if (LocationManager.isGranted) {
            showProgress()
        }else{
            requestLocationPermission()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 102) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recreate()
            } else {
                // Permission denied, handle the situation accordingly
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun requestLocationPermission() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Kami Perlu Mengakses lokasi anda untuk dapat menggunakan aplikasi ini")
            setTitle("Izin Lokasi Di Perlukan")
            setPositiveButton("OK") { _, _ ->
                ActivityCompat.requestPermissions(
                    this@FetchUserLocation,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    102
                )
            }
            setNegativeButton("Cancel") { _, _ ->
                finish()
            }
        }.create().show()
    }


    private fun showProgress() {
        Handler(Looper.getMainLooper()).postDelayed({
            val progress = binding.loadingSplash.progress + 12
            binding.loadingSplash.progress = progress

            if (progress < 100) {
                showProgress()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        },200)
    }

    @Suppress("DEPRECATION")
    private fun getAddressDetail(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latitude, longitude, 1
                ) { addresses ->
                    if (addresses.isNotEmpty()) {
                        val address = addresses[0]

                        LocationManager.addressLocation = address.getAddressLine(0)
                    }
                }
            } else {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (addresses!!.isNotEmpty()) {
                    val address = addresses[0]
                    LocationManager.addressLocation = address.getAddressLine(0)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationManager.isGranted = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    berandaViewModel.setLatLng(location.latitude,location.longitude)
                    LocationManager.lat = location.latitude
                    LocationManager.long = location.longitude
                    getAddressDetail(location.latitude,location.longitude)
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
            LocationManager.isGranted = false
        }
    }



}