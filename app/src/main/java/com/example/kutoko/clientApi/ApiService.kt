package com.example.kutoko.clientApi

import com.example.kutoko.data.*
import retrofit2.Call
import retrofit2.http.*

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

    @GET("recommendation")
    suspend fun getRecommendation(
        @Header("Authorization") token: String?,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("total_row") total_row: Int,
        @Query("page") page: Int
    ) : StoreResponse

    @GET("businesses/{idToko}")
    fun getDetailStore(
        @Header("Authorization") token: String,
        @Path("idToko") idToko:String
    ): Call<DetailStoreResponse>

    @GET("products")
    fun getListProduct(
        @Header("Authorization") token: String,
        @Query("business_id") q: String
    ) : Call<ListProductResponse>
}