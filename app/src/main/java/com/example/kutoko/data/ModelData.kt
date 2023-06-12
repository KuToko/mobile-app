package com.example.kutoko.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Favorite(
    var Id: String,
    var name : String,
    var upvotes : String,
    var avatar : String,
    var categories : String
) : Parcelable

@Parcelize
data class User(
    var userId: String? = null,
    var name: String? = null,
    var token: String? = null
) : Parcelable

data class Location(
    var lat: Double,
    var lon: Double,
    var address: String,
    var isUsed: Boolean
)

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
    @field:SerializedName("is_voted")
    val is_voted : Boolean,
    @field:SerializedName("upvotes")
    val upvotes : Int,
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
