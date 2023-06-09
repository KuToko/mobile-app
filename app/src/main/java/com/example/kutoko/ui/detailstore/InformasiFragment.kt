package com.example.kutoko.ui.detailstore

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kutoko.R
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.apiResponse.DetailStoreResponse
import com.example.kutoko.databinding.FragmentInformasiBinding
import com.example.kutoko.util.TokenManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Response

class InformasiFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentInformasiBinding? = null
    private val binding get() = _binding!!

    private var idToko: String? = null
    private var tokoLat: Double = 0.0
    private var tokolong: Double = 0.0


    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            idToko = it.getString("idToko")
            tokoLat = it.getDouble("tokoLat")
            tokolong = it.getDouble("tokoLong")
        }

        val token = TokenManager.token
        getDetailtoko(token.toString(), idToko.toString())

        val mapFragment = childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    private fun getDetailtoko(token: String, idToko: String) {
        val detailClient = ApiConfig.getApiService().getDetailStore("Bearer ${token}", idToko)
        detailClient.enqueue(object : retrofit2.Callback<DetailStoreResponse> {
            override fun onResponse(call: Call<DetailStoreResponse>, response: Response<DetailStoreResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    setDetailInformasi(response.body())
                } else {
                    Log.e("Informasi Toko", "onResponseFailure Token : ${token}")
                    Log.e("Informasi Toko", "onResponseFailure idToko : ${idToko}")
                    Log.e("Informasi Toko", "onResponseFailure Response : ${response.raw()}")
                }
            }

            override fun onFailure(call: Call<DetailStoreResponse>, t: Throwable) {
                //_isLoading.value = false
                Log.e("Detail Toko", "onEnqueueFailure: ${t.message}")
            }
        })
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        val latLngToko = LatLng(tokoLat,tokolong)
        map.addMarker(MarkerOptions().position(latLngToko))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngToko, 11.6f))
    }

    private fun setDetailInformasi(it: DetailStoreResponse?) {
        binding.infoAlamat.text = it?.data?.address
        binding.jamSenin.text = if(it?.data?.isMondayOpen == true){"${it.data.mondayStartTime} - ${it.data.mondayEndTime}"} else{it?.data?.mondayNotes}
        binding.jamSelasa.text = if(it?.data?.isTuesdayOpen == true){"${it.data.tuesdayStartTime} - ${it.data.tuesdayEndTime}"} else{it?.data?.tuesdayNotes}
        binding.jamRabu.text = if(it?.data?.isWednesdayOpen == true){"${it.data.wednesdayStartTime} - ${it.data.wednesdayEndTime}"} else{it?.data?.wednesdayNotes}
        binding.jamKamis.text = if(it?.data?.isThursdayOpen == true){"${it.data.thursdayStartTime} - ${it.data.thursdayEndTime}"} else{it?.data?.thursdayNotes}
        binding.jamJumat.text = if(it?.data?.isFridayOpen == true){"${it.data.fridayStartTime} - ${it.data.fridayEndTime}"} else{it?.data?.fridayNotes}
        binding.jamSabtu.text = if(it?.data?.isSaturdayOpen == true){"${it.data.saturdayStartTime} - ${it.data.saturdayEndTime}"} else{it?.data?.saturdayNotes}
        binding.jamMinggu.text = if(it?.data?.isSundayOpen == true){"${it.data.sundayStartTime} - ${it.data.sundayEndTime}"} else{it?.data?.sundayNotes}
    }

}