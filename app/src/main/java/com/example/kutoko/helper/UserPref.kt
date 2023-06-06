package com.example.kutoko.helper

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.map

class UserPref private constructor(private val dataStore: DataStore<Preferences>) {

    private val LOGIN_TOKEN  = stringPreferencesKey("token")

    fun getToken(): Flow<String> {
        return dataStore.data.map { Preferences ->
            Preferences[LOGIN_TOKEN] ?: ""
        }
    }

    suspend fun saveToken(token : String){
        dataStore.edit { Preferences ->
            Preferences[LOGIN_TOKEN]  = token
        }
    }

    suspend fun deleteToken(){
        dataStore.edit { Preferences ->
            Preferences[LOGIN_TOKEN]  = ""
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: UserPref? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPref {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPref(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}