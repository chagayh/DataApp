package com.example.myapplication

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException


class PostPrettyNameWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        val serverInterface = ServerHolder.serverInterface
        val prettyName = inputData.getString("key_pretty_name")?: return Result.retry()
        val token = inputData.getString("key_user_token")?: return Result.retry()
        val request = SetUserPrettyNameRequest(prettyName)

        return try {
            val header = "token $token"
            val response = serverInterface.setUserPrettyName(header, request).execute()
            if (!response.isSuccessful) {
                Result.failure(Data.Builder().putInt("key_request_error", response.code()).build())
            } else {
                val userAsJson = Gson().toJson(response.body())
                val outPutData = Data.Builder()
                    .putString("key_pretty_info", userAsJson)
                    .build()
                Result.success(outPutData)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Result.retry()
        }
    }
}