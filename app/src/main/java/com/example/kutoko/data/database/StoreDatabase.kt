package com.example.kutoko.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [ListStoreItem::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)


abstract class StoreDatabase: RoomDatabase() {
    abstract fun storeRemote() : StoreRemote
    abstract fun remoteKeysDAO() : RemoteKeysDAO

    companion object {
        @Volatile
        private var INSTANCE : StoreDatabase? = null


        @JvmStatic
        fun getDatabase(context: Context): StoreDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoreDatabase::class.java,"store_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}