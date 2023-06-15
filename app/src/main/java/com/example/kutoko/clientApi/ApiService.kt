package com.example.kutoko.clientApi

import com.example.kutoko.data.*
import com.example.kutoko.data.apiResponse.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("recommendation/similar/{idToko}")
    fun getSimiliarStore(
        @Header("Authorization") token: String,
        @Path("idToko") idToko: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): Call<SimiliarBusinessResponse>

    @GET("businesses/search")
    fun findStore(
        @Header("Authorization") token: String,
        @Query("q") q: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): Call<FindBusinessResponse>

    @Multipart
    @POST("products")
    fun uploadProduct(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("name") name : String,
        @Part("price") price : Int,
        @Part("business_id") business_id : String
    ) : Call<UploadProductResponse>


    @GET("businesses/my/business")
    fun getMyStore(
        @Header("Authorization") token: String?
    ) : Call<MyStoreResponse>

}