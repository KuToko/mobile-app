package com.example.kutoko.data.database.favoriteDatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "userFavorite")
@Parcelize
data class ListFavoriteItem (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idStore")
    var idStore: Int = 0,

    @ColumnInfo(name = "id")
    var id: String?,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "avatar")
    val avatar : String?,

    @ColumnInfo(name = "upvotes")
    val upvotes : Int?,

    @ColumnInfo(name = "categories")
    val categories : String? = null,

) : Parcelable