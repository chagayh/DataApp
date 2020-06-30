package com.example.myapplication

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException

class GetUserWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        val serverInterface = ServerHolder.serverInterface
        val token = inputData.getString("key_user_token") ?: return Result.retry()

        return try {
            val response: Response<UserResponse> = serverInterface.getUser(token).execute()
            if (response.code() != 200 || !response.isSuccessful) {
                Result.retry()
            } else {
                val user: UserResponse? = response.body()
                val tokenAsJson = Gson().toJson(user)
                val outputData: Data = Data.Builder()
                    .putString("key_output_user", tokenAsJson)
                    .build()

                Result.success(outputData)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Result.retry()
        }
    }

}