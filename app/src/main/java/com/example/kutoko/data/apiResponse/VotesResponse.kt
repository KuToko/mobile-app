package com.example.kutoko.data.apiResponse

import com.google.gson.annotations.SerializedName


data class UserVotesResponse(
    @field:SerializedName("error")
    val error : Boolean,
    @field:SerializedName("message")
    val message : String,
    @field:SerializedName("data")
    val data : List<VotesItem>
)


data class VotesItem(
    @field:SerializedName("business_id")
    val business_id : String
)

data class DeleteVotesResponse(
    @field:SerializedName("error")
    val error : Boolean,
    @field:SerializedName("message")
    val message : String
)

data class PostVotesResponse(
    @field:SerializedName("error")
    val error : Boolean,
    @field:SerializedName("message")
    val message : String
)