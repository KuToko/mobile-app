package com.example.kutoko.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginResult,

	@field:SerializedName("message")
	val message: String
)

data class LoginResult(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("avatar")
	val avatar: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("token")
	val token: String
)
