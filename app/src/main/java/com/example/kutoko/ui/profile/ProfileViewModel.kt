package com.example.kutoko.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.MyStoreResponse
import retrofit2.Call
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private val _myStoreResponse = MutableLiveData<MyStoreResponse>()

    val myStoreResponse : LiveData<MyStoreResponse> = _myStoreResponse

    private val _errorResponse = MutableLiveData<Boolean>()
    val errorResponse : LiveData<Boolean> = _errorResponse

    internal fun checkMyStore(token: String?){
        val client = ApiConfig.getApiService().getMyStore("Bearer $token")
        client.enqueue(object : retrofit2.Callback<MyStoreResponse>{
            override fun onResponse(
                call: Call<MyStoreResponse>,
                response: Response<MyStoreResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    _myStoreResponse.value = MyStoreResponse(responseBody.error,responseBody.data)
                    _errorResponse.value = false
                }else{
                    _errorResponse.value = true
                    Log.e("Response my Store Failed","Get MyBusiness Failed: ${response.message()} and $token")
                }
            }
            override fun onFailure(call: Call<MyStoreResponse>, t: Throwable) {
                Log.e("On Failure Get My Store", "On Failure Message : $t")
            }

        })
    }
}