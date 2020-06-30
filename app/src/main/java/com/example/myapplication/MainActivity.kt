package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private val appContext: MyApp
        get() = applicationContext as MyApp
    private val editText: EditText
        get() = findViewById(R.id.editText)
    private val enterBtn: Button
            get () = findViewById(R.id.enterBtn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText.visibility = View.INVISIBLE
        enterBtn.visibility = View.INVISIBLE

        checkFirstLogin()
    }

    private fun checkFirstLogin(){
        if (appContext.appSp.getIsFirstLogin()){
            askForUserName()
        }
    }

    private fun askForUserName(){
        editText.visibility = View.VISIBLE
        enterBtn.visibility = View.VISIBLE
    }
}
