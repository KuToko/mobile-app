package com.example.kutoko.data.remoteDAO.recomendationRemote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.kutoko.clientApi.ApiService
import com.example.kutoko.data.database.ListRecommendationItem
import com.example.kutoko.data.database.remoteRecomendation.RecomendationRemoteKeys
import com.example.kutoko.data.database.recomendationDatabase.RecomendationDatabase
import com.example.kutoko.util.LocationManager
import com.example.kutoko.util.TokenManager
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagingApi::class)
class RecomendationRemoteMediator (
    private val database : RecomendationDatabase,
    private val apiService: ApiService
    ) : RemoteMediator<Int, ListRecommendationItem> ()
{
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListRecommendationItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null )
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val responseData = apiService.getRecommendation("Bearer " + TokenManager.token,LocationManager.lat,LocationManager.long, state.config.pageSize,page).data
            Log.d("token", "${TokenManager.token}")
            Log.d("lokasii", "${LocationManager.lat}")
            val dataStore = ArrayList<ListRecommendationItem>()

            for (i in responseData.indices){
                var kategori = ""


                for (j in responseData[i].categories.indices){
                    if (j == 3) {
                        break
                    }
                    kategori += responseData[i].categories[j].name + ", "
                }

                val recomendation = ListRecommendationItem(responseData[i].id, responseData[i].name,responseData[i].google_maps_rating,responseData[i].avatar,responseData[i].is_voted,responseData[i].upvotes,kategori,responseData[i].distance_in_m,responseData[i].distance_in_km)
                dataStore.add(recomendation)
            }
            delay(1000)

            val endOfPaginationReached = responseData.isEmpty()
            Log.d("ResponseData Rekomendasi", "${responseData.size} jumlah")
            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    database.recomendationRemoteKeysDAO().deleteRemoteKeys()
                    database.recomendationRemote().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.map {
                    RecomendationRemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }.toList()
                database.recomendationRemoteKeysDAO().insertAll(keys)
                database.recomendationRemote().addRecomendation(dataStore)

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            Log.d("ResponseData di rekomendasi", "gagal ${exception.message}")
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ListRecommendationItem>): RecomendationRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.recomendationRemoteKeysDAO().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ListRecommendationItem>): RecomendationRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.recomendationRemoteKeysDAO().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ListRecommendationItem>): RecomendationRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.recomendationRemoteKeysDAO().getRemoteKeysId(id)
            }
        }
    }
}