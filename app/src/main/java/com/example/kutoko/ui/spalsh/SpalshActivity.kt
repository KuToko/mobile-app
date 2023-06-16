package com.example.kutoko.ui.spalsh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.TranslateAnimation
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.MyStoreResponse
import com.example.kutoko.data.UserPreference
import com.example.kutoko.databinding.ActivitySpalshBinding
import com.example.kutoko.ui.auth.LoginActivity
import com.example.kutoko.ui.userLocation.FetchUserLocation
import com.example.kutoko.util.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpalshActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpalshBinding
    private lateinit var mUserPreference: UserPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topAnim = TranslateAnimation(0f,0f,-200f,0f)
        topAnim.startOffset = 500
        topAnim.duration = 1900
        binding.ivSpalsh.startAnimation(topAnim)

        val bottAnim = TranslateAnimation(0f,0f,200f,0f)
        bottAnim.startOffset = 500
        bottAnim.duration = 1900
        binding.tvAppName.startAnimation(bottAnim)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({ // Start the next activity
            val intent = Intent(this@SpalshActivity, LoginActivity::class.java)
            startActivity(intent)
            mUserPreference = UserPreference(this)
            if (mUserPreference.getUser().token.toString().isNotEmpty()){

                val client = ApiConfig.getApiService().getMyStore("Bearer ${mUserPreference.getUser().token.toString()}")
                client.enqueue(object : Callback<MyStoreResponse> {
                    override fun onResponse(
                        call: Call<MyStoreResponse>,
                        response: Response<MyStoreResponse>
                    ) {
                        if (response.isSuccessful){
                            TokenManager.token = mUserPreference.getUser().token.toString()
                            startActivity(Intent(this@SpalshActivity, FetchUserLocation::class.java))
                            finish()
                        }else{
                            mUserPreference.deleteData()
                            TokenManager.token = null
                            startActivity(Intent(this@SpalshActivity,LoginActivity::class.java))
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<MyStoreResponse>, t: Throwable) {
                        mUserPreference.deleteData()
                        TokenManager.token = null
                        startActivity(Intent(this@SpalshActivity,LoginActivity::class.java))
                        finish()
                    }

                })
            }

        }, 2000)

    }
}