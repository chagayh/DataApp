package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServerInterface {
    @GET("/users/{user_name}/token")
    fun getToken(@Path("userName") userNAme: String): Call<TokenResponse>

    @GET("/user/")
    fun getUser(): Call<UserResponse>
}