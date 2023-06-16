package com.example.kutoko.data.apiResponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ListProductResponse(

	@field:SerializedName("pagination")
	val pagination: PaginationListProduct? = null,

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class PaginationListProduct(

	@field:SerializedName("perPage")
	val perPage: Int? = null,

	@field:SerializedName("from")
	val from: Int? = null,

	@field:SerializedName("to")
	val to: Int? = null,

	@field:SerializedName("currentPage")
	val currentPage: String? = null
)

data class DataItem(

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("product_image")
	val productImage: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("business_id")
	val businessId: String? = null
)


data class UploadProductResponse(
	@field:SerializedName("message")
	val message: String,
	@field:SerializedName("data")
	val data: List<ProductResponse>
)

data class ProductResponse(
	@field:SerializedName("id")
	val id:String,
	@field:SerializedName("name")
	val name:String,
	@field:SerializedName("price")
	val price: String,
	@field:SerializedName("description")
	val description: String,
	@field:SerializedName("product_image")
	val productImage: String,

)


data class UpdateProductResponse(
	@field:SerializedName("message")
	val message: String,
	@field:SerializedName("data")
	val data: List<ProductResponse>
)

data class DeleteProductResponse(
	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class ProductItem(
	val id_bisnis: String,
	val id:String,
	val name:String,
	val price: String,
	val description: String,
	val productImage: String? = null
) : Parcelable

