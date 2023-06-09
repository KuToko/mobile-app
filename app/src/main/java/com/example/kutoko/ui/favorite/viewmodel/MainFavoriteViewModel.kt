package com.example.kutoko.ui.favorite.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kutoko.data.database.favoriteDatabase.ListFavoriteItem
import com.example.kutoko.data.remoteDAO.favoriteRemote.FavoriteRepository

class MainFavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)

    private val _listFavorite = MutableLiveData<ListFavoriteItem>()

    val listFavoriteItem : LiveData<ListFavoriteItem> = _listFavorite

    fun getAllFavorite() : LiveData<List<ListFavoriteItem>> = mFavoriteRepository.getAllFavorite()


    fun insertFavorite(favoriteStore: ListFavoriteItem){
        mFavoriteRepository.insertFavorite(favoriteStore)
    }

    fun deleteFavorite(idStore: String){
        mFavoriteRepository.deleteFavorite(idStore)
    }

    fun setList(listFavorite : ListFavoriteItem){
        _listFavorite.value = listFavorite
    }
}