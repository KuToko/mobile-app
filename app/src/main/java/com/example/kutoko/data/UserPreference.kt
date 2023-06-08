package com.example.kutoko.data

import android.content.Context

internal class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val USERID = "userId"
        private const val NAME = "name"
        private const val TOKEN = "token"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: User){
        val editor = preferences.edit()
        editor.putString(USERID,value.userId)
        editor.putString(NAME,value.name)
        editor.putString(TOKEN,value.token)
        editor.apply()
    }

    fun getUser(): User {
        val user = User()
        user.userId = preferences.getString(USERID,"")
        user.name = preferences.getString(NAME,"")
        user.token = preferences.getString(TOKEN,"")

        return user
    }

    fun deleteData(){
        preferences.edit().clear().apply()
    }
}