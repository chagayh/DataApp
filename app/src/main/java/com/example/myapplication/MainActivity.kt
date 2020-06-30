package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

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

        invisibleComps()

        checkFirstLogin()

        setButton()

    }

    private fun invisibleComps(){
        editText.visibility = View.INVISIBLE
        enterBtn.visibility = View.INVISIBLE
    }

    private fun setButton(){
        enterBtn.setOnClickListener {
            when {
                editText.text.toString() == "" -> Toast.makeText(this, "Enter User Name", Toast.LENGTH_LONG)
                    .show()
                else -> {appContext.appSp.storeUserName(editText.text.toString())
                    invisibleComps()
                }
            }
        }
    }

    private fun checkFirstLogin(){
        if (appContext.appSp.getUserName() == null){
            askForUserName()
        }
    }

    private fun askForUserName(){
        editText.visibility = View.VISIBLE
        enterBtn.visibility = View.VISIBLE
    }
}
