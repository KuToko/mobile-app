package com.example.kutoko.ui.beranda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BerandaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _latitude = MutableLiveData<Double>()
    val latitude : LiveData<Double> = _latitude

    private val _longitude = MutableLiveData<Double>()
    val longitude : LiveData<Double> = _longitude




    internal fun setLatLng(lat : Double, long : Double){
        _latitude.value = lat
        _longitude.value = long
    }
}