package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    val imageLiveData: MutableLiveData<String> = MutableLiveData()
}