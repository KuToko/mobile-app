package com.example.kutoko.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kutoko.data.StoreCategory
import com.google.gson.annotations.SerializedName


@Entity(tableName = "stores")
data class ListStoreItem (

    @PrimaryKey
    @field:SerializedName("id")
    val id : String,

    @field:SerializedName("name")
    val name : String,

    @field:SerializedName("google_maps_rating")
    val google_maps_rating : String?,

    @field:SerializedName("avatar")
    val avatar : String,

    @field:SerializedName("categories")
    val categories : String? = null,

    @field:SerializedName("distance_in_m")
    val distance_in_m : Double,

    @field:SerializedName("distance_in_km")
    val distance_in_km: Double

)
