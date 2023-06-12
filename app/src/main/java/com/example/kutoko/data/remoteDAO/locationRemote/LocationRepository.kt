package com.example.kutoko.data.remoteDAO.locationRemote

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.kutoko.data.database.locationDatabase.LocationDao
import com.example.kutoko.data.database.locationDatabase.LocationRoomDatabase
import com.example.kutoko.data.database.locationDatabase.LocationUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocationRepository(application: Application) {
    private val mLocationDao: LocationDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = LocationRoomDatabase.getDatabase(application)
        mLocationDao = db.locationDao()
    }

    fun getLocation(isUsed: Boolean): LiveData<List<LocationUser>> = mLocationDao.getLocation(isUsed)

    fun insertLocation(location: LocationUser){
        executorService.execute{ mLocationDao.insertLocation(location)}
    }

    fun deleteLocation(lat: Double, lon: Double){
        executorService.execute{mLocationDao.deleteLocation(lat,lon)}

    }
}