package com.example.kutoko.data.apiResponse

import com.google.gson.annotations.SerializedName

data class FindBusinessResponse(

	@field:SerializedName("pagination")
	val pagination: PaginationSearchItem? = null,

	@field:SerializedName("data")
	val data: List<DataSearchItem>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CategoriesSearchItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class PaginationSearchItem(

	@field:SerializedName("perPage")
	val perPage: Int? = null,

	@field:SerializedName("from")
	val from: Int? = null,

	@field:SerializedName("to")
	val to: Int? = null,

	@field:SerializedName("currentPage")
	val currentPage: String? = null
)

data class DataSearchItem(

	@field:SerializedName("distance_in_m")
	val distanceInM: Double,

	@field:SerializedName("is_voted")
	val isVoted: String? = null,

	@field:SerializedName("upvotes")
	val upvotes: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("distance_in_km")
	val distanceInKm: Double,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesSearchItem?>? = null,

	@field:SerializedName("google_maps_rating")
	val googleMapsRating: Any? = null
)
