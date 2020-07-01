package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class GetUserWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        val serverInterface = ServerHolder.serverInterface
        val token = inputData.getString("key_user_token") ?: return Result.retry()

        return try {
            val header = "token $token"
            val response: Response<UserResponse> = serverInterface.getUser(header).execute()
            Log.d("token", "token = $token, response.isSuccessful = ${response.isSuccessful}")
            if (!response.isSuccessful) {
                Result.retry()
            } else {
                val user: User? = response.body()?.data
                val userAsJson = Gson().toJson(user)
                val outputData: Data = Data.Builder()
                    .putString("key_user_info", userAsJson)
                    .build()

                Result.success(outputData)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Result.retry()
        }
    }

}