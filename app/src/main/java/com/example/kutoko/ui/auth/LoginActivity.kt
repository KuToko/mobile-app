package com.example.kutoko.ui.auth

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.kutoko.MainActivity
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.LoginResponse
import com.example.kutoko.databinding.ActivityLoginBinding
import com.example.kutoko.helper.UserPref
import com.example.kutoko.helper.ViewModelFactory
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private var correctEmail = false
    private var correctPassword = false

    private val Password_Pattern = Pattern.compile("^" +
            //"(?=.*[@#$%^&+=])" +     // at least 1 special character
            "(?=\\S+$)" +            // no white spaces
            ".{8,}" +                // at least 8 characters
            "$")

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPref.getInstance(dataStore)

        authViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(AuthViewModel::class.java)

        binding.tvRegisterNow.setOnClickListener{
            val moveIntent = Intent(this, RegisterActivity::class.java)
            startActivity(moveIntent)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val sandi = binding.loginSandi.text.toString()
            val bodyLogin = mapOf("email" to email, "password" to sandi)
            login(bodyLogin)
        }

        binding.loginEmail.addTextChangedListener(object : TextWatcher {
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

        binding.loginSandi.addTextChangedListener(object : TextWatcher {
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

    private fun login(bodyLogin: Map<String, String>) {
        //showLoading(true)
        val client = ApiConfig.getApiService().postLogin(bodyLogin)
        client.enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    if (responseBody.message == "error"){
                        shorAlertDialog()
                    } else{
                        authViewModel.saveToken(responseBody.data.token)
                        val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(moveIntent)
                        finish()
                    }
                } else{
                    Log.e("Login Failed", "Login onFailure: ${response.body()?.data}")
                    shorAlertDialog()
                }
                //_isLoading.value = false
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                //_isLoading.value = false
                Log.e("enqueue Failed", "Login onFailure: ${t.message}")
                shorAlertDialog()
            }

        })
    }

    private fun shorAlertDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login Gagal")
        builder.setMessage("Silahkan Cek Email & Password \nCoba Beberapa Saat Lagi Apabila Tidak Ada Kesalahan")

        builder.setNegativeButton("OK"){ dialog : DialogInterface, _: Int ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun setBtnEnabled(binding: ActivityLoginBinding){
        binding.loginBtn.isEnabled = correctEmail && correctPassword
    }
}