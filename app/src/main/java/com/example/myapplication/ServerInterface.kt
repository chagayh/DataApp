package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ServerInterface {
    @GET("/users/{user_name}/token/")
    fun getToken(@Path("user_name") userName: String): Call<TokenResponse>

    @GET("/user/")
    fun getUser(@Header("Authorization") auth: String): Call<UserResponse>
}