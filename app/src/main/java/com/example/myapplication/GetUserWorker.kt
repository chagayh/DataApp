package com.example.myapplication

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class GetUserWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        val serverInterface = ServerHolder.serverInterface
    }

}