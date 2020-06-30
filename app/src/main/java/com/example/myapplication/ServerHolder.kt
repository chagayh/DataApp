package com.example.myapplication

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServerHolder {
    const val BASE_URL = "https://hujipostpc2019.pythonanywhere.com"

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val serverInterface: ServerInterface by lazy {
        retrofit.create(ServerInterface::class.java)
    }
}

/*


class ServerHolder (private val serverInterface: ServerInterface){

    object Singleton {
        private val client: OkHttpClient = OkHttpClient.Builder()
            .build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://hujipostpc2019.pythonanywhere.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val serverInterface: ServerInterface = retrofit.create(ServerInterface::class.java)

    }

    fun getInstance(): ServerHolder {
        return ServerHolder(serverInterface)
    }
}

*/
