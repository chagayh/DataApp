package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private val appContext: MyApp
        get() = applicationContext as MyApp
    private val editText: EditText
        get() = findViewById(R.id.editText)
    private val enterBtn: Button
            get () = findViewById(R.id.enterBtn)
    private val textView: TextView
        get () = findViewById(R.id.textView)
    private var token: String? = null
    private var userInfo: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkFirstLogin()

        setButton()

    }

    private fun requestUserInfo(token: String){
        val id = appContext.requestUserInfo(token)
        val liveDataOfUserWorker = WorkManager.getInstance().getWorkInfoByIdLiveData(id)
        liveDataOfUserWorker.observe(this, Observer { value ->
            if (value == null || value.state != WorkInfo.State.SUCCEEDED) {
                Log.d("userInfo", "problems")
                return@Observer
            } else {
                val userAsJson = value.outputData.getString("key_user_info")
                val userType = object : TypeToken<User>(){}.type
                if (userAsJson != null){
                    val user = Gson().fromJson<User>(userAsJson, userType)
                    userInfo = User(user.username, user.pretty_name, user.image_url)
                    updateUI()
                    Log.d("userInfo", "user name = ${userInfo!!.pretty_name}")
                    Log.d("userInfo", "pretty name empty = ${userInfo!!.pretty_name.isNullOrEmpty()}")
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun requestToken(userName: String){
        val id = appContext.requestToken(userName)
        val liveDataOfTokenWorker = WorkManager.getInstance().getWorkInfoByIdLiveData(id)
        liveDataOfTokenWorker.observe(this, Observer { value ->
            if (value == null || value.state != WorkInfo.State.SUCCEEDED) {
                return@Observer
            } else {
                val token = value.outputData.getString("key_user_token")
                if (token != null){
                    appContext.appSp.storeToken(token)
                    Log.d("tokenData", "$token")
                    requestUserInfo(token)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(){
        enterBtn.text = "CHANGE USER NAME"
//        val msg = if (init) "welcome " else "welcome back "
        if (!userInfo?.pretty_name.isNullOrEmpty()){
            textView.text = "welcome ${userInfo?.pretty_name}"
        } else {
            textView.text = "welcome ${userInfo?.username}"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setButton(){
        enterBtn.setOnClickListener {
            when (enterBtn.text){
                "ENTER" -> {
                    if (editText.text.isEmpty()) {
                        Toast.makeText(this, "Enter User Name", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        textView.text = "loading ..."
                        requestToken(editText.text.toString())
                        editText.text.clear()
                    }
                }
                else -> {   // change the pretty name field
                    requestPostName()
                    appContext.appSp.storeUserName(editText.text.toString())
                    enterBtn.text = "CHANGE USER NAME"
                    textView.text = "Welcome " + editText.text.toString()
                    editText.text.clear()
                }
            }
        }
    }

    private fun requestPostName(prettyName: String){
        val id = appContext.requestPostPrettyName(prettyName, token)
        val liveDataOfTokenWorker = WorkManager.getInstance().getWorkInfoByIdLiveData(id)
        liveDataOfTokenWorker.observe(this, Observer { value ->
            if (value == null || value.state != WorkInfo.State.SUCCEEDED) {
                return@Observer
            } else {
                val userAsJson = value.outputData.getString("key_pretty_info")
                val userType = object : TypeToken<User>(){}.type
                if (userAsJson != null){
                    val user = Gson().fromJson<User>(userAsJson, userType)
                    userInfo.pretty_name = user.pretty_name
                    Log.d("prettyName", "pretty name = ${userInfo!!.pretty_name}")
                    updateUI()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun checkFirstLogin(){
        token = appContext.appSp.getUserToken()
        if (token == null){
            enterBtn.text = "ENTER"
            textView.text = "Enter User Name First"
        } else {
            textView.text = "loading ..."
            requestUserInfo(token!!)
            enterBtn.text = "change your pretty name"
        }
    }
}
