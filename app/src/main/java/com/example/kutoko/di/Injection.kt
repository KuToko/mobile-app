package com.example.kutoko.di

import android.content.Context
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.database.StoreDatabase
import com.example.kutoko.data.remoteDAO.StoreRepository

object Injection {
    fun provideRepository(context: Context) : StoreRepository {
        val database = StoreDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoreRepository(database,apiService)
    }
}