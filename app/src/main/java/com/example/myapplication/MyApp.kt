package com.example.myapplication

import android.app.Application
import androidx.work.*
import java.util.*

class MyApp: Application() {
    val appSp: AppSp
        get() = AppSp(this)

    fun requestToken(userName: String): UUID{
        val tokenWorkTagUniqueId: UUID = UUID.randomUUID()

        val tokenWork = OneTimeWorkRequest.Builder(GetTokenWorker::class.java)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setInputData(Data.Builder().putString("key_user_name", userName).build())
            .addTag(tokenWorkTagUniqueId.toString())
            .build()

        WorkManager.getInstance().enqueue(tokenWork)
        return tokenWork.id
    }

    fun requestUserInfo(token: String): UUID{
        val userWorkTagUniqueId: UUID = UUID.randomUUID()

        val userWork = OneTimeWorkRequest.Builder(GetUserWorker::class.java)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setInputData(Data.Builder().putString("key_user_token", token).build())
            .addTag(userWorkTagUniqueId.toString())
            .build()

        WorkManager.getInstance().enqueue(userWork)
        return userWork.id
    }

    fun requestPostPrettyName(name: String, token: String?): UUID {
        val userWorkTagUniqueId: UUID = UUID.randomUUID()

        val nameWork = OneTimeWorkRequest.Builder(PostPrettyNameWorker::class.java)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setInputData(Data.Builder().putString("key_user_token", token).putString("key_pretty_name", name).build())
            .addTag(userWorkTagUniqueId.toString())
            .build()

        WorkManager.getInstance().enqueue(nameWork)
        return nameWork.id
    }
}