package com.example.myapplication

import android.annotation.SuppressLint
import android.media.session.MediaSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.WorkSource
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    private val appContext: MyApp
        get() = applicationContext as MyApp
    private val editText: EditText
        get() = findViewById(R.id.editText)
    private val enterBtn: Button
            get () = findViewById(R.id.enterBtn)
    private val textView: TextView
        get () = findViewById(R.id.textView)
    private val mutableLiveDataToken = MutableLiveData<String>()    // for the token
    private val mutableLiveDataUser = MutableLiveData<String>()    // for the user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setObservers()

        checkFirstLogin()

        setButton()

    }

    @SuppressLint("SetTextI18n")
    private fun setObservers(){
        // token observer
        mutableLiveDataToken.observe(this, Observer { value ->
            if (value == null){
                textView.text = "null token in live data"
            } else {
                textView.text = "token = $value"
                appContext.appSp.storeToken(value)
            }
        })
    }

    private fun requestToken(userName: String){
        val id = appContext.requestToken(userName)
        val liveDataOfTokenWorker = WorkManager.getInstance().getWorkInfoByIdLiveData(id)
        liveDataOfTokenWorker.observe(this, Observer { value ->
            if (value == null || value.state != WorkInfo.State.SUCCEEDED) {
                return@Observer
            } else {
                value.outputData.getString("key_user_token")?.let { appContext.appSp.storeToken(it) }
            }
            Log.d("tokenData", "${value.outputData.getString("key_user_token")}")
        })
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
                        requestToken(editText.text.toString())
                        editText.text.clear()
                    }
                }
                else -> {
                    appContext.appSp.storeUserName(editText.text.toString())
                    enterBtn.text = "CHANGE USER NAME"
                    textView.text = "Welcome " + editText.text.toString()
                    editText.text.clear()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkFirstLogin(){
        if (appContext.appSp.getUserToken() == null){
            enterBtn.text = "ENTER"
            textView.text = "Enter User Name First"
        } else {
            textView.text = "Welcome " + appContext.appSp.getUserName()
            enterBtn.text = "CHANGE USER NAME"
        }
    }
}
