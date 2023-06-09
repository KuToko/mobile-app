package com.example.kutoko.ui.beranda

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kutoko.data.database.ListRecommendationItem
import com.example.kutoko.data.remoteDAO.recomendationRemote.RecomendationRepository
import com.example.kutoko.di.Injection

class RecomendationPageViewModel(recomendationRepository: RecomendationRepository) : ViewModel() {
    val recomendStore : LiveData<PagingData<ListRecommendationItem>> = recomendationRepository.getRecomendation().cachedIn(viewModelScope)
}


class ViewModelFactoryRecomendation(private val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecomendationPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecomendationPageViewModel(Injection.provideRecomendRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}