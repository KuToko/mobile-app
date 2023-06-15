package com.example.kutoko.data.remoteDAO.nearbyRemote

import android.util.Log
import android.widget.Toast
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.kutoko.clientApi.ApiService
import com.example.kutoko.data.database.ListStoreItem
import com.example.kutoko.data.database.remoteDatabase.RemoteKeys
import com.example.kutoko.data.database.nearbyStoreDatabase.StoreDatabase
import com.example.kutoko.util.LocationManager
import com.example.kutoko.util.TokenManager
import kotlinx.coroutines.delay


@OptIn(ExperimentalPagingApi::class)
class StoreRemoteMediator (
    private val database : StoreDatabase,
    private val apiService: ApiService
    ) : RemoteMediator<Int, ListStoreItem>()
{
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListStoreItem>
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
            val responseData = apiService.getStore("Bearer " + TokenManager.token,LocationManager.lat, LocationManager.long, state.config.pageSize,page).data
            val dataStore = ArrayList<ListStoreItem>()
            Log.d("Ini coba test lokasi", "${LocationManager.long}, ${LocationManager.addressLocation}")
            for (i in responseData.indices){
                var kategori = ""

                for (j in responseData[i].categories.indices){
                    if (j == 4){
                        break
                    }
                    kategori += responseData[i].categories[j].name + ", "
                }
                val store = ListStoreItem(responseData[i].id,responseData[i].name,responseData[i].google_maps_rating,responseData[i].avatar,responseData[i].is_voted,responseData[i].upvotes,kategori,responseData[i].distance_in_m,responseData[i].distance_in_km)
                dataStore.add(store)
            }
            val endOfPaginationReached = responseData.isEmpty()
            Log.d("ResponseData Toko Terdekat", "${responseData.size} jumlah")
            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    database.remoteKeysDAO().deleteRemoteKeys()
                    database.storeRemote().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }.toList()
                database.remoteKeysDAO().insertAll(keys)
                database.storeRemote().addStories(dataStore)
            }
//            delay(4000)
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            Log.d("ResponseData di toko terdekat", "gagal ${exception.message}")
            database.remoteKeysDAO().deleteRemoteKeys()
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ListStoreItem>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDAO().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ListStoreItem>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDAO().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ListStoreItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDAO().getRemoteKeysId(id)
            }
        }
    }
}