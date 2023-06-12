package com.example.kutoko.ui.userLocation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kutoko.R
import com.example.kutoko.data.database.locationDatabase.LocationUser
import com.example.kutoko.databinding.ActivityAddLocationBinding
import com.example.kutoko.ui.userLocation.locationViewModel.LocationViewModelFactory
import com.example.kutoko.ui.userLocation.locationViewModel.MainLocationViewModel
import com.example.kutoko.util.LocationManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class AddLocation : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var marker : Marker
    private lateinit var binding: ActivityAddLocationBinding
    private lateinit var mainLocationViewModel: MainLocationViewModel

    private var curLat: Double = LocationManager.lat
    private var curLon: Double = LocationManager.long

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(false)

        mainLocationViewModel = obtainMainViewModel(this@AddLocation)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_selector) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btAdd.setOnClickListener {
            showLoading(true)
            uploadLocation()
        }
    }

    private fun obtainMainViewModel(activity: AppCompatActivity): MainLocationViewModel {
        val factory = LocationViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory)[MainLocationViewModel::class.java]
    }

    private fun uploadLocation() {
        val address = getAddress(LatLng(curLat,curLon))
        if (address != null){
            val locationUser = LocationUser(0,curLat,curLon,address,false)
            Toast.makeText(this@AddLocation,"${locationUser.address}",Toast.LENGTH_LONG).show()
            mainLocationViewModel.insertLocation(locationUser)
            val builder = AlertDialog.Builder(this@AddLocation)
            builder.setTitle("Location Success to Add !!")
            builder.setMessage("Add Location is Success")
            builder.setPositiveButton("OK") { _, _ ->
                val intent = Intent(this@AddLocation,LocationList::class.java)
                startActivity(intent)
                finish()
            }
            builder.show()
            showLoading(false)
        }else{
            val builder = AlertDialog.Builder(this@AddLocation)
            builder.setTitle("Add Location Failed")
            builder.setMessage("Gagal Menambah Lokasi. Coba Lagi !!")
            builder.setPositiveButton("OK") { _, _ ->
                recreate()
                finish()
            }
            builder.show()
            showLoading(false)
        }


    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        getMyLocation()
        mMap.uiSettings.isZoomControlsEnabled = true

        //custom marker
        mMap.setOnMapClickListener {
            mMap.clear()

            if (this::marker.isInitialized){
                marker.remove()
            }

            marker = mMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .draggable(true)
                    .title(getAddress(it))
                    .snippet(getAddress(it))
            ) as Marker
            curLat = it.latitude
            curLon = it.longitude
            Toast.makeText(this@AddLocation,"Lokasi Saat Ini Lat ${it.latitude} & Lokasi Lon ${it.longitude}",Toast.LENGTH_LONG).show()
        }

        mMap.setOnMyLocationButtonClickListener {
            updateCurrentLocation()
            Toast.makeText(this@AddLocation,"Lokasi Lat $curLat & Lokasi Lon $curLon",Toast.LENGTH_LONG).show()
            true
        }

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
            mMap.isMyLocationEnabled = true
            updateCurrentLocation()
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ).toString()
            )
        }
    }

    private fun updateCurrentLocation(){
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.clear()

            if (this::marker.isInitialized){
                marker.remove()
            }

            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Check if a valid location is available
                    if (location != null) {
                        // Update the map camera to the current location
                        val latLng = LatLng(location.latitude, location.longitude)
                        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10f)
                        mMap.animateCamera(cameraUpdate)

                        marker = mMap.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .draggable(true)
                                .title("Lokasi Anda Saat Ini")
                                .snippet(getAddress(latLng))
                        ) as Marker

                        curLat = latLng.latitude
                        curLon = latLng.longitude
                    }
                }

        }
    }

    @Suppress("DEPRECATION")
    private fun getAddress(lat: LatLng): String? {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat.latitude, lat.longitude,1)
        return list?.get(0)?.getAddressLine(0)
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}