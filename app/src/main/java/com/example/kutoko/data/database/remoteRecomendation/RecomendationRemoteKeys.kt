package com.example.kutoko.data.database.remoteRecomendation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recomend_remote_keys")
data class RecomendationRemoteKeys(
    @PrimaryKey val id: String,
    val prevKey : Int?,
    val nextKey : Int?
)