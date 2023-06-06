package com.example.kutoko.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kutoko.data.StoreList


@Dao
interface StoreRemote {
    @Query("SELECT * FROM stores")
    fun getAllStores(): PagingSource<Int, ListStoreItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStories(stores: List<ListStoreItem>)

    @Query("DELETE FROM stores")
    suspend fun deleteAll()

}