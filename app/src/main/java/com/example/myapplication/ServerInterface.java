package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ServerInterface {

    @GET("/users/0")
    Call<TokenResponse> connectivityCheck();



    @GET("/users/{user_id}")
    Call<TokenResponse> getUser(@Path("user_id") String userId);


    @GET("/todos")
    Call<List<TokenResponse>> getAllTicketsForUser(@Query("user_id") String userId);


    // the result will be the ticket as was created in the server (e.g. change in the "id" field, etc)
    @POST("/todos")
    Call<TokenResponse> insertNewTicket(@Body TokenResponse token);

}
