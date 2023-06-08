package com.example.kutoko.data.remoteDAO

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.kutoko.clientApi.ApiService
import com.example.kutoko.data.database.ListStoreItem
import com.example.kutoko.data.database.nearbyStoreDatabase.StoreDatabase

class StoreRepository(private val storeDatabase: StoreDatabase, private val apiService: ApiService) {
    fun getStore() : LiveData<PagingData<ListStoreItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = StoreRemoteMediator(storeDatabase,apiService),
            pagingSourceFactory = {
                storeDatabase.storeRemote().getAllStores()
            }
        ).liveData
    }
}