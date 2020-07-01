package com.example.myapplication

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class InputFragment: Fragment() {
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var textView: TextView
    private lateinit var appContext: MyApp
    private var token: String? = null
    private var userInfo: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appContext = activity?.applicationContext as MyApp
        Log.d("inputFragment", "app context token = ${appContext.appSp.getUserToken()}")
        editText = view.findViewById(R.id.editText)
        button = view.findViewById(R.id.button)
        textView = view.findViewById(R.id.textView)

        Log.d("inputFragment", "app context = $appContext")

        checkFirstLogin()

        setButton()


    }

    @SuppressLint("SetTextI18n")
    private fun requestUserInfo(){
        val id = token?.let { appContext.requestUserInfo(it) }
        val liveDataOfUserWorker = id?.let { WorkManager.getInstance().getWorkInfoByIdLiveData(it) }
        liveDataOfUserWorker?.observe(this, Observer { value ->
            if (value == null || value.state != WorkInfo.State.SUCCEEDED) {
                return@Observer
            } else {
                val userAsJson = value.outputData.getString("key_user_info")
                val userType = object : TypeToken<User>(){}.type
                if (userAsJson != null){
                    checkForError(value)
                    val user = Gson().fromJson<User>(userAsJson, userType)
                    userInfo = User(user.username, user.pretty_name, user.image_url)
                    updateUI()
                    Log.d("userInfo", "user name = ${userInfo!!.pretty_name}")
                    Log.d("userInfo", "pretty name empty = ${userInfo!!.pretty_name.isNullOrEmpty()}")
                }
            }
        })
    }

    private fun checkForError(value: WorkInfo){
        val errorVal = value.outputData.getInt("key_token_error", -1)
        if (errorVal != -1){
            Toast.makeText(activity, "error - $errorVal", Toast.LENGTH_SHORT)
                .show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun requestToken(userName: String){
        val id = appContext.requestToken(userName)
        val liveDataOfTokenWorker = WorkManager.getInstance().getWorkInfoByIdLiveData(id)
        liveDataOfTokenWorker.observe(this, Observer { value ->
            if (value == null || value.state != WorkInfo.State.SUCCEEDED) {
                return@Observer
            } else {
                checkForError(value)
                val token = value.outputData.getString("key_user_token")
                if (token != null){
                    appContext.appSp.storeToken(token)
                    this.token = token
                    Log.d("tokenData", "$token")
                    requestUserInfo()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun requestPostName(prettyName: String){
        val id = appContext.requestPostPrettyName(prettyName, token)
        Log.d("prettyName", "pretty name = $prettyName, token = $token")
        val liveDataOfPrettyWorker = WorkManager.getInstance().getWorkInfoByIdLiveData(id)
        liveDataOfPrettyWorker.observe(this, Observer { value ->
            if (value == null || value.state != WorkInfo.State.SUCCEEDED) {
                return@Observer
            } else {
                checkForError(value)
                val userAsJson = value.outputData.getString("key_pretty_info")
                Log.d("prettyName", "userAsJson = $userAsJson")
                val userType = object : TypeToken<User>(){}.type
                if (userAsJson != null){
                    val user = Gson().fromJson<User>(userAsJson, userType)
                    userInfo?.pretty_name = user.pretty_name
                    updateUI()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setButton(){
        button.setOnClickListener {
            when (button.text){
                "ENTER" -> {
                    if (editText.text.isEmpty()) {
                        Toast.makeText(activity, "Enter User Name", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        textView.text = "loading ..."
                        requestToken(editText.text.toString())
                        editText.hint = "pretty name"
                        editText.text.clear()
                    }
                }
                else -> {   // change the pretty name field
                    Log.d("setButton", "begin else")
                    if (editText.text.isEmpty()) {
                        Toast.makeText(activity, "Enter pretty name", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Log.d("setButton", "in else")
                        requestPostName(editText.text.toString())
                        editText.text.clear()
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(){
        Log.d("updateUI", "begin updateUI")
        button.text = "change your pretty name"
        if (!userInfo?.pretty_name.isNullOrEmpty()){
            Log.d("updateUI", "pretty name = ${userInfo?.pretty_name}")
            textView.text = "welcome ${userInfo?.pretty_name}"
        } else {
            Log.d("updateUI", "pretty name = ${userInfo?.pretty_name}")
            textView.text = "welcome ${userInfo?.username}"
        }
        if (userInfo?.image_url != null){
            Log.d("inputFragment", "post value with - value = ${userInfo?.image_url}")
            appContext.viewModel.imageLiveData.postValue(userInfo?.image_url)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkFirstLogin(){
        token = appContext.appSp.getUserToken()
        if (token == null){
            button.text = "ENTER"
            textView.text = "Enter User Name First"
        } else {
            textView.text = "loading ..."
            requestUserInfo()
            button.text = "change your pretty name"
            editText.hint = "pretty name"
        }
    }
}