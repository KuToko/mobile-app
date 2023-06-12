package com.example.kutoko.data.database.favoriteDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ListFavoriteItem::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDAO():FavoriteDAO

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteDatabase::class.java,"favorite_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteDatabase
        }
    }

}