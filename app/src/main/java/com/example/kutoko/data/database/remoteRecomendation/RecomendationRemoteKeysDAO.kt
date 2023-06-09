package com.example.kutoko.data.database.remoteRecomendation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RecomendationRemoteKeysDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RecomendationRemoteKeys>)

    @Query("SELECT * FROM recomend_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): RecomendationRemoteKeys?

    @Query("DELETE FROM recomend_remote_keys")
    suspend fun deleteRemoteKeys()
}