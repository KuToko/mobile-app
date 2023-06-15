package com.example.kutoko.ui.spalsh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.TranslateAnimation
import com.example.kutoko.databinding.ActivitySpalshBinding
import com.example.kutoko.ui.auth.LoginActivity

class SpalshActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpalshBinding
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

            // Close the current activity
            finish()
        }, 4000)

    }
}