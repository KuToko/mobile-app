package com.example.kutoko.data.remoteDAO.favoriteRemote

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.kutoko.data.database.favoriteDatabase.FavoriteDAO
import com.example.kutoko.data.database.favoriteDatabase.FavoriteDatabase
import com.example.kutoko.data.database.favoriteDatabase.ListFavoriteItem
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDAO
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDAO()
    }

    fun getAllFavorite() : LiveData<List<ListFavoriteItem>> = mFavoriteDao.getAllFavorite()

    fun insertFavorite(store: ListFavoriteItem){
        executorService.execute { mFavoriteDao.insertFavorite(store) }
    }

    fun deleteFavorite(storeId: String){
        executorService.execute { mFavoriteDao.deleteFavorite(storeId)}
    }

}