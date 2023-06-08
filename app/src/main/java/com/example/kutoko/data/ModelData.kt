package com.example.kutoko.data

import android.os.Parcelable
import com.example.kutoko.data.database.ListStoreItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    var userId: String? = null,
    var name: String? = null,
    var token: String? = null
) : Parcelable

data class StoreResponse(
    @field:SerializedName("error")
    val error : Boolean,
    @field:SerializedName("message")
    val message : String,
    @field:SerializedName("data")
    val data : List<StoreList>
)

data class StoreList(
    @field:SerializedName("id")
    val id : String,
    @field:SerializedName("name")
    val name : String,
    @field:SerializedName("google_maps_rating")
    val google_maps_rating : String?,
    @field:SerializedName("avatar")
    val avatar : String,
    @field:SerializedName("categories")
    val categories : List<StoreCategory>,
    @field:SerializedName("distance_in_m")
    val distance_in_m : Double,
    @field:SerializedName("distance_in_km")
    val distance_in_km: Double
)

data class StoreCategory(
    @field:SerializedName("id")
    val id : String,
    @field:SerializedName("name")
    val name : String
)
