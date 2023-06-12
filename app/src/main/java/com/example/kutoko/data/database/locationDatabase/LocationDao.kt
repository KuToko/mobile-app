package com.example.kutoko.data.database.locationDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLocation(location : LocationUser)

    @Query("DELETE FROM userLocation WHERE lat = :lat & lon = :lon")
    fun deleteLocation(lat: Double, lon: Double)

    @Query("SELECT * FROM userLocation WHERE isUsed = :isUsed")
    fun getLocation(isUsed: Boolean): LiveData<List<LocationUser>>
}