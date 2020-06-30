package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class GetTokenWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {

    override fun doWork(): Result {
        val serverInterface = ServerHolder.serverInterface
        val userName = inputData.getString("key_user_name") ?: return Result.retry()

        return try {
            val response: Response<TokenResponse> = serverInterface.getToken(userName).execute()
            if (!response.isSuccessful) {
                Log.d("tokenData", "not response.isSuccessful")
                Result.retry()
            } else {
                val token: TokenResponse? = response.body()
                val tokenAsJson = Gson().toJson(token)
                val outputData: Data = Data.Builder()
                    .putString("key_user_token", tokenAsJson)
                    .build()

                Result.success(outputData)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Result.retry()
        }
    }
}