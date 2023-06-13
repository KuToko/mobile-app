package com.example.kutoko.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


object TokenManager {
    var token: String? = ""
}

object LocationManager {
    var lat: Double = 0.0
    var long: Double = 0.0
    var addressLocation: String? = ""
    var isGranted : Boolean = false
}

object FavoriteManager {
    var isAdded: Boolean = false
}


