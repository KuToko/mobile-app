package com.example.kutoko.clientApi

import com.example.kutoko.data.StoreResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("businesses")
    suspend fun getStore(
        @Header("Authorization") token: String?,
        @Query("latitude") latitude: Double = -7.775241177136506,
        @Query("longitude") longitude: Double = 110.393442675452,
        @Query("total_row") total_row: Int,
        @Query("page") page: Int

    ) : StoreResponse
}