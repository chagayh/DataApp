package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val appContext: MyApp
        get() = applicationContext as MyApp
    private val editText: EditText
        get() = findViewById(R.id.editText)
    private val enterBtn: Button
            get () = findViewById(R.id.enterBtn)
    private val textView: TextView
        get () = findViewById(R.id.textView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkFirstLogin()

        setButton()

    }

    @SuppressLint("SetTextI18n")
    private fun setButton(){
        enterBtn.setOnClickListener {
            when {
                editText.text.toString() == "" -> Toast.makeText(this, "Enter User Name", Toast.LENGTH_LONG)
                    .show()
                else -> {appContext.appSp.storeUserName(editText.text.toString())
                    enterBtn.text = "CHANGE USER NAME"
                    textView.text = "Welcome " + editText.text.toString()
                    editText.text.clear()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkFirstLogin(){
        if (appContext.appSp.getUserName() == null){
            enterBtn.text = "ENTER"
            textView.text = "Hi New User"
            askForUserName()
        } else {
            textView.visibility = View.VISIBLE
            textView.text = "Welcome " + appContext.appSp.getUserName()
            enterBtn.text = "CHANGE USER NAME"
        }
    }

    private fun askForUserName(){
        editText.visibility = View.VISIBLE
        enterBtn.visibility = View.VISIBLE
    }
}
