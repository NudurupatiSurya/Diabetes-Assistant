package com.suryanudurupati.sugar.MainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _listening = MutableLiveData<Boolean>(false)
    val listening : LiveData<Boolean> = _listening

    fun ChangeMicState(){
        _listening.value = !_listening.value!!
    }
}