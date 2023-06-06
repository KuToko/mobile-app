package com.example.kutoko.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kutoko.helper.UserPref
import kotlinx.coroutines.launch

class AuthViewModel(private val pref: UserPref) : ViewModel() {

    fun saveToken(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }

}