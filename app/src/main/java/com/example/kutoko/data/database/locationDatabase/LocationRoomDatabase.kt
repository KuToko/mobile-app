package com.example.kutoko.data.database.locationDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [LocationUser::class], version = 1)
abstract class LocationRoomDatabase : RoomDatabase() {
    abstract fun locationDao():LocationDao

    companion object{
        @Volatile
        private var INSTANCE: LocationRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): LocationRoomDatabase{
            if (INSTANCE == null){
                synchronized(LocationRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, LocationRoomDatabase::class.java,"location_database")
                        .build()
                }

            }
            return INSTANCE as LocationRoomDatabase
        }
    }
}