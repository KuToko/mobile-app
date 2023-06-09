package com.example.kutoko.data.database.recomendationDatabase

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kutoko.data.database.ListRecommendationItem
import com.example.kutoko.data.database.ListStoreItem

@Dao
interface RecomendationRemote {
    @Query("SELECT * FROM recommendation")
    fun getAllRecomendation(): PagingSource<Int, ListRecommendationItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecomendation(stores: List<ListRecommendationItem>)

    @Query("DELETE FROM recommendation")
    suspend fun deleteAll()
}