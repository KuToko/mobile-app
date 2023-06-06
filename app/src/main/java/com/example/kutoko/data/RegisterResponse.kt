package com.example.kutoko.data

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: RegisterResult,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class RegisterResult(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("is_super_admin")
	val isSuperAdmin: Boolean,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
