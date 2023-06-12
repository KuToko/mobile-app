package com.example.kutoko.ui.userLocation.locationViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kutoko.data.database.favoriteDatabase.ListFavoriteItem
import com.example.kutoko.data.database.locationDatabase.LocationUser
import com.example.kutoko.data.remoteDAO.favoriteRemote.FavoriteRepository
import com.example.kutoko.data.remoteDAO.locationRemote.LocationRepository

class MainLocationViewModel(application: Application) : ViewModel() {
    private val mLocationRepository : LocationRepository = LocationRepository(application)

    fun getLocation(isUsed: Boolean) : LiveData<List<LocationUser>> = mLocationRepository.getLocation(isUsed)

    fun insertLocation(locationUser : LocationUser){
        mLocationRepository.insertLocation(locationUser)
    }

    fun deleteLocation(lat: Double, lon: Double){
        mLocationRepository.deleteLocation(lat,lon)
    }

}
