package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

class AppSp(context: Context) {
    private var isFirstLogin: Boolean = true
    private var token: String? = null
    private var spFile: SharedPreferences


    companion object {
        private const val SP_LOCATION_FILE_NAME: String = "sp_location"
        private const val KEY_FIRST_LOGIN: String = "first_login"
    }

    init {
        spFile = context.getSharedPreferences(SP_LOCATION_FILE_NAME, Context.MODE_PRIVATE)
        loadFirstLogin()
    }

    private fun loadFirstLogin(){
        isFirstLogin = spFile.getBoolean(KEY_FIRST_LOGIN, true)
    }

    fun getIsFirstLogin(): Boolean{
        return isFirstLogin
    }

    fun setFirstLogin() {
        this.isFirstLogin = false
        storeFirstLogin()
    }

    private fun storeFirstLogin(){
        val edit = spFile.edit()
        edit.putBoolean(KEY_FIRST_LOGIN, isFirstLogin)
            .apply()
    }


}