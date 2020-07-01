package com.example.myapplication

import retrofit2.Call
import retrofit2.http.*

data class SetUserPrettyNameRequest(
    var prettyName: String?
)

interface ServerInterface {
    @GET("/users/{user_name}/token/")
    fun getToken(@Path("user_name") userName: String): Call<TokenResponse>

    @GET("/user/")
    fun getUser(@Header("Authorization") auth: String): Call<UserResponse>

    @Headers("Content-Type: application/json")
    @POST("/user/edit/")
    fun setUserPrettyName(@Header("Authorization") auth: String,
                          @Body request: SetUserPrettyNameRequest) : Call<UserResponse>
}