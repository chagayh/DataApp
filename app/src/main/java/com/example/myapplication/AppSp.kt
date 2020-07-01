package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

class AppSp(context: Context) {
    private var token: String? = null
    private var userName: String? = null
    private var spFile: SharedPreferences


    companion object {
        private const val SP_LOCATION_FILE_NAME: String = "sp_location"
        private const val KEY_TOKEN: String = "user_token"
        private const val KEY_USER_NAME: String = "user_name"
    }

    init {
        spFile = context.getSharedPreferences(SP_LOCATION_FILE_NAME, Context.MODE_PRIVATE)
        loadUserName()
        loadToken()
    }

    fun getUserToken(): String? {
        return token
    }

    private fun loadUserName(){
        userName = spFile.getString(KEY_USER_NAME, null)
    }

    private fun loadToken(){
        token = spFile.getString(KEY_TOKEN, null)
    }

    fun storeToken(str: String){
        val edit = spFile.edit()
        edit.putString(KEY_TOKEN, str)
            .apply()
        token = str
    }
}