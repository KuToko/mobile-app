package com.example.kutoko.ui.auth

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.apiResponse.RegisterResponse
import com.example.kutoko.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private var correctEmail = false
    private var correctPassword = false

    private val Password_Pattern = Pattern.compile("^" +
            //"(?=.*[@#$%^&+=])" +     // at least 1 special character
            "(?=\\S+$)" +            // no white spaces
            ".{8,}" +                // at least 8 characters
            "$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(false)
        binding.registBtn.setOnClickListener {
            showLoading(true)
            val username = binding.registUsername.text.toString()
            val name = binding.registName.text.toString()
            val email = binding.registEmail.text.toString()
            val sandi = binding.registSandi.text.toString()

            val bodyRegist = mapOf("username" to username, "name" to name, "email" to email, "password" to sandi)
            register(bodyRegist)
        }

        binding.registEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    correctEmail = true
                    setBtnEnabled(binding)
                }else {
                    correctEmail = false
                    setBtnEnabled(binding)
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }

        })

        binding.registEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && Password_Pattern.matcher(s).matches()){
                    correctPassword = true
                    setBtnEnabled(binding)
                }else {
                    correctPassword = false
                    setBtnEnabled(binding)
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }

        })

    }

    private fun register(bodyRegist: Map<String, String>) {

        //showLoading(true)
        val client = ApiConfig.getApiService().postRegister(bodyRegist)
        client.enqueue(object : retrofit2.Callback<RegisterResponse>{
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    if (responseBody.message == "error"){
                        showLoading(false)
                        shorAlertDialog()
                    } else{
                        showLoading(false)
                        val moveIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(moveIntent)
                        finish()
                    }
                } else{
                    showLoading(false)
                    Log.e("Login Failed", "Login onFailure: ${response.body()?.data}")
                    shorAlertDialog()
                }
                //_isLoading.value = false
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                //_isLoading.value = false
                showLoading(false)
                Log.e("enqueue Failed", "Login onFailure: ${t.message}")
                shorAlertDialog()
            }

        })
    }

    private fun shorAlertDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Register Gagal")
        builder.setMessage("Email Sudah Terpakai \nCoba Beberapa Saat Lagi Apabila Tidak Ada Kesalahan")

        builder.setNegativeButton("OK"){ dialog : DialogInterface, _: Int ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun setBtnEnabled(binding: ActivityRegisterBinding){
        binding.registBtn.isEnabled = correctEmail && correctPassword
                && binding.registUsername.text.isNotEmpty() && binding.registName.text.isNotEmpty()
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}