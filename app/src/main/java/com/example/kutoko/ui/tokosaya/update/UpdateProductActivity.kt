package com.example.kutoko.ui.tokosaya.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kutoko.databinding.ActivityUpdateProductBinding

class UpdateProductActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateProductBinding

    companion object {
        const val PRODUCT_ITEM = "PRODUCT_ITEM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)

        setContentView(binding.root)



    }
}