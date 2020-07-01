package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
//    private val appContext: MyApp
//        get() = applicationContext as MyApp
//    private val editText: EditText
//        get() = findViewById(R.id.editText)
//    private val enterBtn: Button
//            get () = findViewById(R.id.enterBtn)
//    private val textView: TextView
//        get () = findViewById(R.id.textView)
//    private val userImage: ImageView
//        get () = findViewById(R.id.userImage)
//    private var token: String? = null
//    private var userInfo: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.top_frame, ImageFragment())
            .replace(R.id.bottom_frame, InputFragment())
            .commit()

//        checkFirstLogin()

//        setButton()

    }

//    @SuppressLint("SetTextI18n")
//    private fun requestUserInfo(token: String){
//        val id = appContext.requestUserInfo(token)
//        val liveDataOfUserWorker = WorkManager.getInstance().getWorkInfoByIdLiveData(id)
//        liveDataOfUserWorker.observe(this, Observer { value ->
//            if (value == null || value.state != WorkInfo.State.SUCCEEDED) {
//                textView.text = "error occurred code = ${value.outputData.getInt("key_user_error", -1)}"
//                return@Observer
//            } else {
//                val userAsJson = value.outputData.getString("key_user_info")
//                val userType = object : TypeToken<User>(){}.type
//                if (userAsJson != null){
//                    val user = Gson().fromJson<User>(userAsJson, userType)
//                    userInfo = User(user.username, user.pretty_name, user.image_url)
//                    updateUI()
//                    Log.d("userInfo", "user name = ${userInfo!!.pretty_name}")
//                    Log.d("userInfo", "pretty name empty = ${userInfo!!.pretty_name.isNullOrEmpty()}")
//                }
//            }
//        })
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun requestToken(userName: String){
//        val id = appContext.requestToken(userName)
//        val liveDataOfTokenWorker = WorkManager.getInstance().getWorkInfoByIdLiveData(id)
//        liveDataOfTokenWorker.observe(this, Observer { value ->
//            if (value == null || value.state != WorkInfo.State.SUCCEEDED) {
//                textView.text = "error occurred code = ${value.outputData.getInt("key_user_error", -1)}"
//                return@Observer
//            } else {
//                val token = value.outputData.getString("key_user_token")
//                if (token != null){
//                    appContext.appSp.storeToken(token)
//                    Log.d("tokenData", "$token")
//                    requestUserInfo(token)
//                }
//            }
//        })
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun updateUI(){
//        enterBtn.text = "CHANGE USER NAME"
//        if (!userInfo?.pretty_name.isNullOrEmpty()){
//            textView.text = "welcome ${userInfo?.pretty_name}"
//        } else {
//            textView.text = "welcome ${userInfo?.username}"
//        }
//        if (userInfo?.image_url != null){
//            Glide.with(this)
//                .load(ServerHolder.BASE_URL + userInfo?.image_url)
//                .into(userImage)
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun setButton(){
//        enterBtn.setOnClickListener {
//            when (enterBtn.text){
//                "ENTER" -> {
//                    if (editText.text.isEmpty()) {
//                        Toast.makeText(this, "Enter User Name", Toast.LENGTH_LONG)
//                            .show()
//                    } else {
//                        textView.text = "loading ..."
//                        requestToken(editText.text.toString())
//                        editText.text.clear()
//                    }
//                }
//                else -> {   // change the pretty name field
//                    if (editText.text.isEmpty()) {
//                        Toast.makeText(this, "Enter User Name", Toast.LENGTH_LONG)
//                            .show()
//                    } else {
//                        requestPostName(editText.text.toString())
//                        appContext.appSp.storeUserName(editText.text.toString())
//                        editText.text.clear()
//                    }
//                }
//            }
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun requestPostName(prettyName: String){
//        val id = appContext.requestPostPrettyName(prettyName, token)
//        val liveDataOfPrettyWorker = WorkManager.getInstance().getWorkInfoByIdLiveData(id)
//        liveDataOfPrettyWorker.observe(this, Observer { value ->
//            if (value == null || value.state != WorkInfo.State.SUCCEEDED) {
//                textView.text = "error occurred code = ${value.outputData.getInt("key_user_error", -1)}"
//                return@Observer
//            } else {
//                val userAsJson = value.outputData.getString("key_pretty_info")
//                Log.d("prettyName", "userAsJson = $userAsJson")
//                val userType = object : TypeToken<User>(){}.type
//                if (userAsJson != null){
//                    val user = Gson().fromJson<User>(userAsJson, userType)
//                    userInfo?.pretty_name = user.pretty_name
//                    updateUI()
//                }
//            }
//        })
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun checkFirstLogin(){
//        token = appContext.appSp.getUserToken()
//        if (token == null){
//            enterBtn.text = "ENTER"
//            textView.text = "Enter User Name First"
//        } else {
//            textView.text = "loading ..."
//            requestUserInfo(token!!)
//            enterBtn.text = "change your pretty name"
//        }
//    }
}
