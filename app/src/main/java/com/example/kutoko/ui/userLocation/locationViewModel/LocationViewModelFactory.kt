package com.example.kutoko.ui.userLocation.locationViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kutoko.ui.favorite.viewmodel.MainFavoriteViewModel

class LocationViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory(){
    companion object{
        @Volatile
        private var INSTANCE: LocationViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): LocationViewModelFactory {
            if (INSTANCE == null) {
                synchronized(LocationViewModelFactory::class.java) {
                    INSTANCE = LocationViewModelFactory(application)
                }
            }
            return INSTANCE as LocationViewModelFactory
        }
    }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainLocationViewModel::class.java)) {
            return MainLocationViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}
