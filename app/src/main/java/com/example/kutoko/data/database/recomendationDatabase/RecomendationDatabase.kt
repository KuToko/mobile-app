package com.example.kutoko.data.database.recomendationDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kutoko.data.database.ListRecommendationItem
import com.example.kutoko.data.database.remoteRecomendation.RecomendationRemoteKeys
import com.example.kutoko.data.database.remoteRecomendation.RecomendationRemoteKeysDAO


@Database(
    entities = [ListRecommendationItem::class, RecomendationRemoteKeys::class],
    version = 2,
    exportSchema = false
)



abstract class RecomendationDatabase : RoomDatabase() {
    abstract fun recomendationRemote() : RecomendationRemote
    abstract fun recomendationRemoteKeysDAO() : RecomendationRemoteKeysDAO

    companion object {
        @Volatile
        private var INSTANCE : RecomendationDatabase? = null


        @JvmStatic
        fun getDatabase(context: Context): RecomendationDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecomendationDatabase::class.java,"recomendation_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}

