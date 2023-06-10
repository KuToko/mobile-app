package com.example.kutoko.data

import com.google.gson.annotations.SerializedName

data class DetailStoreResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CategoriesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class Data(

	@field:SerializedName("saturday_end_time")
	val saturdayEndTime: Any? = null,

	@field:SerializedName("sunday_start_time")
	val sundayStartTime: Any? = null,

	@field:SerializedName("upvotes")
	val upvotes: Int? = null,

	@field:SerializedName("saturday_notes")
	val saturdayNotes: String? = null,

	@field:SerializedName("user_name")
	val userName: Any? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("tuesday_notes")
	val tuesdayNotes: String? = null,

	@field:SerializedName("released_at")
	val releasedAt: Any? = null,

	@field:SerializedName("monday_notes")
	val mondayNotes: String? = null,

	@field:SerializedName("is_sunday_open")
	val isSundayOpen: Any? = null,

	@field:SerializedName("sunday_notes")
	val sundayNotes: String? = null,

	@field:SerializedName("tuesday_start_time")
	val tuesdayStartTime: Any? = null,

	@field:SerializedName("wednesday_end_time")
	val wednesdayEndTime: Any? = null,

	@field:SerializedName("business_username")
	val businessUsername: String? = null,

	@field:SerializedName("friday_start_time")
	val fridayStartTime: Any? = null,

	@field:SerializedName("saturday_start_time")
	val saturdayStartTime: Any? = null,

	@field:SerializedName("thursday_end_time")
	val thursdayEndTime: Any? = null,

	@field:SerializedName("is_friday_open")
	val isFridayOpen: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null,

	@field:SerializedName("tuesday_end_time")
	val tuesdayEndTime: Any? = null,

	@field:SerializedName("friday_notes")
	val fridayNotes: String? = null,

	@field:SerializedName("place_id")
	val placeId: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("google_maps_rating")
	val googleMapsRating: Any? = null,

	@field:SerializedName("is_saturday_open")
	val isSaturdayOpen: Any? = null,

	@field:SerializedName("wednesday_notes")
	val wednesdayNotes: String? = null,

	@field:SerializedName("business_name")
	val businessName: String? = null,

	@field:SerializedName("thursday_notes")
	val thursdayNotes: String? = null,

	@field:SerializedName("user_email")
	val userEmail: Any? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("monday_end_time")
	val mondayEndTime: Any? = null,

	@field:SerializedName("is_tuesday_open")
	val isTuesdayOpen: Any? = null,

	@field:SerializedName("monday_start_time")
	val mondayStartTime: Any? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("is_wednesday_open")
	val isWednesdayOpen: Any? = null,

	@field:SerializedName("wednesday_start_time")
	val wednesdayStartTime: Any? = null,

	@field:SerializedName("added_from_system")
	val addedFromSystem: Boolean? = null,

	@field:SerializedName("is_thursday_open")
	val isThursdayOpen: Any? = null,

	@field:SerializedName("user_id")
	val userId: Any? = null,

	@field:SerializedName("thursday_start_time")
	val thursdayStartTime: Any? = null,

	@field:SerializedName("postal_code")
	val postalCode: String? = null,

	@field:SerializedName("sunday_end_time")
	val sundayEndTime: Any? = null,

	@field:SerializedName("link_theme")
	val linkTheme: String? = null,

	@field:SerializedName("friday_end_time")
	val fridayEndTime: Any? = null,

	@field:SerializedName("is_monday_open")
	val isMondayOpen: Any? = null
)
