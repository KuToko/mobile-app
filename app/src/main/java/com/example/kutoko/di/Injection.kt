package com.example.kutoko.di

import android.content.Context
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.database.nearbyStoreDatabase.StoreDatabase
import com.example.kutoko.data.database.recomendationDatabase.RecomendationDatabase
import com.example.kutoko.data.remoteDAO.nearbyRemote.StoreRepository
import com.example.kutoko.data.remoteDAO.recomendationRemote.RecomendationRepository

object Injection {
    fun provideRepository(context: Context) : StoreRepository {
        val database = StoreDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoreRepository(database,apiService)
    }

    fun provideRecomendRepository(context: Context) : RecomendationRepository {
        val database = RecomendationDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return RecomendationRepository(database,apiService)
    }
}