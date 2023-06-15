package com.example.kutoko.ui.tokosaya.beranda

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.MyStore
import com.example.kutoko.data.MyStoreResponse
import com.example.kutoko.data.apiResponse.DataItem
import com.example.kutoko.data.apiResponse.ListProductResponse
import com.example.kutoko.util.TokenManager
import retrofit2.Call
import retrofit2.Response


class BerandaTokoSayaViewModel : ViewModel() {

    private val _myStore = MutableLiveData<MyStore>()
    val myStore : LiveData<MyStore> = _myStore

    private val _myProduct = MutableLiveData<List<DataItem>?>()
    val myProduct: LiveData<List<DataItem>?> = _myProduct

    private val _errorResponse = MutableLiveData<Boolean>()
    val errorResponse : LiveData<Boolean> = _errorResponse


    init {
        val client = ApiConfig.getApiService().getMyStore("Bearer ${TokenManager.token}")
        client.enqueue(object : retrofit2.Callback<MyStoreResponse>{
            override fun onResponse(
                call: Call<MyStoreResponse>,
                response: Response<MyStoreResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    _myStore.value = responseBody.data[0]
                }else{
                    Log.e("Response my Store Failed","Get MyBusiness Failed: ${response.message()} and ${TokenManager.token}")
                }
            }
            override fun onFailure(call: Call<MyStoreResponse>, t: Throwable) {
                Log.e("On Failure Get My Store", "On Failure Message : $t")
            }

        })
    }

    internal fun fetchProduct(token: String?,idStore: String){
        val client = ApiConfig.getApiService().getListProduct("Bearer $token", idStore)
        client.enqueue(object : retrofit2.Callback<ListProductResponse>{
            override fun onResponse(
                call: Call<ListProductResponse>,
                response: Response<ListProductResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    if (responseBody.data != null && responseBody.data.isNotEmpty()){
                        _errorResponse.value = false
                        _myProduct.value = responseBody.data
                    }else{
                        _errorResponse.value = true
                    }
                }else{
                    Log.e("Response my Store Get Product","Get MyProduct Failed: ${response.message()} and $token")
                    _errorResponse.value = true
                }
            }

            override fun onFailure(call: Call<ListProductResponse>, t: Throwable) {
                Log.e("On Failure Get My Product", "On Failure Message : $t")
            }

        })
    }

}