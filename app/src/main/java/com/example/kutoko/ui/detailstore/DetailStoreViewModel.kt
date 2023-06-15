package com.example.kutoko.ui.detailstore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kutoko.clientApi.ApiConfig
import com.example.kutoko.data.apiResponse.UserVotesResponse
import com.example.kutoko.data.apiResponse.VotesItem
import com.example.kutoko.util.TokenManager
import retrofit2.Call
import retrofit2.Response

class DetailStoreViewModel : ViewModel() {

    private val _listVotes = MutableLiveData<List<VotesItem>>()
    val listVotes : LiveData<List<VotesItem>> = _listVotes

    internal fun getVotes(token: String){
        val clinet = ApiConfig.getApiService().getVotes(token)
        clinet.enqueue(object : retrofit2.Callback<UserVotesResponse>{
            override fun onResponse(
                call: Call<UserVotesResponse>,
                response: Response<UserVotesResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    val listVotes = ArrayList<VotesItem>()
                    for (i in responseBody.data.indices){
                        val voteItem = VotesItem(responseBody.data[i].business_id)
                        listVotes.add(voteItem)
                    }
                    _listVotes.value = listVotes
                }else{
                    Log.e("Votes Response Failed","Get Votes Failed: ${response.message()} and ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserVotesResponse>, t: Throwable) {
                Log.e("On Failure Get Votes", "On Failure Message : $t")
            }

        })
    }

}