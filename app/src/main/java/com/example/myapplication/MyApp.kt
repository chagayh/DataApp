package com.example.myapplication

import android.app.Application

class MyApp: Application() {
    val appSp: AppSp
        get() = AppSp(this)
}