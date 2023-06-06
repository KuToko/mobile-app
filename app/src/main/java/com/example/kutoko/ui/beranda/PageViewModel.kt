package com.example.kutoko.ui.beranda

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kutoko.data.database.ListStoreItem
import com.example.kutoko.data.remoteDAO.StoreRepository
import com.example.kutoko.di.Injection

class PageViewModel(storeRepository: StoreRepository) : ViewModel() {

    val store : LiveData<PagingData<ListStoreItem>> = storeRepository.getStore().cachedIn(viewModelScope)

}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PageViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}