package com.example.kutoko.data.remoteDAO.recomendationRemote

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.kutoko.clientApi.ApiService
import com.example.kutoko.data.database.ListRecommendationItem
import com.example.kutoko.data.database.recomendationDatabase.RecomendationDatabase

class RecomendationRepository(private val recomendationDatabase: RecomendationDatabase, private val apiService: ApiService) {

    fun getRecomendation() : LiveData<PagingData<ListRecommendationItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = RecomendationRemoteMediator(recomendationDatabase,apiService),
            pagingSourceFactory = {
                recomendationDatabase.recomendationRemote().getAllRecomendation()
            }
        ).liveData
    }
}


