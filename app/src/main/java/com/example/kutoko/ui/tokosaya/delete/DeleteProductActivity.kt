package com.example.kutoko.ui.tokosaya.delete

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kutoko.MainActivity
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.apiResponse.DeleteProductResponse
import com.example.kutoko.data.apiResponse.ProductItem
import com.example.kutoko.databinding.ActivityDeleteProductBinding
import com.example.kutoko.util.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteProductActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDeleteProductBinding

    companion object {
        const val PRODUCT_ID = "PRODUCT_ID"
        const val PRODUCT_ITEM = "PRODUCT_ITEM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra(PRODUCT_ID)

        @Suppress("DEPRECATION")
        val productItem = intent.getParcelableExtra<ProductItem>(PRODUCT_ITEM)
        if (productItem != null) {
            binding.name.text = productItem.name
            binding.kategori.text = productItem.description
            binding.price.text = productItem.price
        }

        binding.btDelete.setOnClickListener {
            showLoading(true)
            val builder = AlertDialog.Builder(this@DeleteProductActivity)
            builder.setTitle("Delete Produk !!")
            builder.setMessage("Anda Yakin Ingin Mendelete Produk")
            builder.setPositiveButton("OK") {_,_ ->
                if (productId != null) {
                    val token = "Bearer " + TokenManager.token
                    val client = ApiConfig.getApiService().deleteProduct(token,productId)
                    client.enqueue(object : Callback<DeleteProductResponse>{
                        override fun onResponse(
                            call: Call<DeleteProductResponse>,
                            response: Response<DeleteProductResponse>
                        ) {
                            if(response.isSuccessful && response.body() != null){
                                showLoading(false)
                                val intent = Intent(this@DeleteProductActivity,MainActivity::class.java)
                                Toast.makeText(this@DeleteProductActivity,"Data Berhasil Di Hapus",Toast.LENGTH_SHORT).show()
                                intent.putExtra(MainActivity.CHANGE_NAV,true)
                                startActivity(intent)
                                finish()
                            }else{
                                showLoading(false)
                                Toast.makeText(this@DeleteProductActivity,"Data Gagal Di Hapus ${response.code()}",Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<DeleteProductResponse>, t: Throwable) {
                            showLoading(false)
                            Toast.makeText(this@DeleteProductActivity,"Data Gagal Di Hapus ${t.message}",Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
            builder.setNegativeButton("Cancel") { _,_ ->
                showLoading(false)
            }
            builder.show()
        }

    }
    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}