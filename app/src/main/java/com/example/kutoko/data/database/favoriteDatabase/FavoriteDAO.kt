package com.example.kutoko.data.database.favoriteDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favoriteItem: ListFavoriteItem)

    @Query("DELETE FROM userFavorite WHERE id = :id")
    fun deleteFavorite(id : String)

    @Query("SELECT * from userFavorite ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<ListFavoriteItem>>
}