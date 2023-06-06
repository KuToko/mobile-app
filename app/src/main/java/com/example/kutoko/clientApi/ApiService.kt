package com.example.kutoko.clientApi

import com.example.kutoko.data.LoginResponse
import com.example.kutoko.data.RegisterResponse
import com.example.kutoko.data.StoreResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    fun postLogin(
        @Body bodyLogin : Map<String,String>
    ): Call<LoginResponse>

    @POST("auth/register")
    fun postRegister(
        @Body registerLogin : Map<String,String>
    ): Call<RegisterResponse>

    @GET("businesses")
    suspend fun getStore(
        @Header("Authorization") token: String?,
        @Query("latitude") latitude: Double = -7.775241177136506,
        @Query("longitude") longitude: Double = 110.393442675452,
        @Query("total_row") total_row: Int,
        @Query("page") page: Int

    ) : StoreResponse
}