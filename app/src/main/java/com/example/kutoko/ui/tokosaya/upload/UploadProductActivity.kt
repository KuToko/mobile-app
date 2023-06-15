package com.example.kutoko.ui.tokosaya.upload


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.kutoko.MainActivity
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.apiResponse.UploadProductResponse
import com.example.kutoko.databinding.ActivityUploadProductBinding
import com.example.kutoko.util.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadProductActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUploadProductBinding

    private var getFile: File? = null
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri,this@UploadProductActivity)
                getFile = myFile

                val inputStream = contentResolver.openInputStream(uri)
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                val orientation = getImageOrientation(contentResolver, uri)
                binding.ivProductImg.setImageBitmap(bitmap?.let { rotateBitmap(it, orientation) })
            }
        }
    }

    companion object{
        const val BUSINESS_ID = "BUSINESS_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(BUSINESS_ID)

        binding.btUnggahFoto.setOnClickListener {
            startGallery()
        }

        binding.btUpload.setOnClickListener {
            showLoading(true)
            if (id != null){
                uploadProduct(id)
            }else{
                showLoading(false)
                Toast.makeText(this,"Server Error Silahkan Coba Login Ulang",Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun startGallery(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Pilih Foto Produk")
        launcherIntentGallery.launch(chooser)
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun uploadProduct(idBisnis : String){
        if (getFile == null){
            showLoading(false)
            Toast.makeText(this, "Foto Produk Wajib Terisi", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = ApiConfig.getApiService()
        val file = reduceFileImage(getFile as File)

        val description = binding.tieProductDesc.text.toString().toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpg".toMediaType())

        val imageMultipart : MultipartBody.Part = MultipartBody.Part.createFormData(
            "photoProduct",
            file.name,
            requestImageFile
        )



        val price = binding.tieProductPrice.text.toString().toRequestBody("text/plain".toMediaType())
        val name = binding.tieProductName.text.toString().toRequestBody("text/plain".toMediaType())
        val id = idBisnis.toRequestBody("text/plain".toMediaType())

        val token = "Bearer " + TokenManager.token

        val uploadProductRequest = apiService.uploadProduct(token,imageMultipart,id,name,price,description)
        uploadProductRequest.enqueue(object : Callback<UploadProductResponse>{
            override fun onResponse(
                call: Call<UploadProductResponse>,
                response: Response<UploadProductResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.message != "error"){
                        showLoading(false)
                        val builder = AlertDialog.Builder(this@UploadProductActivity)
                        builder.setTitle("Produk Berhasil Di Tambahkan !!")
                        builder.setMessage("Produk Anda Berhasil Di Tambahkan")
                        builder.setPositiveButton("OK") {_,_ ->
                            val intent = Intent(this@UploadProductActivity, MainActivity::class.java)
                            intent.putExtra(MainActivity.CHANGE_NAV,true)
                            finish()
                            startActivity(intent)
                        }
                        builder.show()
                    }else{
                        showLoading(false)
                        Toast.makeText(this@UploadProductActivity, "${response.message()} else 2", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    showLoading(false)
                    Toast.makeText(this@UploadProductActivity, "${response.message()} else 1 error code : ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.d("Uploda Product","${response.body()} ${response.message()} message")
                }
            }

            override fun onFailure(call: Call<UploadProductResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@UploadProductActivity, "${t.message} failure", Toast.LENGTH_SHORT).show()
            }

        })
    }
}