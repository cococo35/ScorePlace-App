package com.android.hanple.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

private val _event: MutableLiveData<String> = MutableLiveData()
    val event: LiveData<String> get() = _event
    fun onClickSignUp(
        name: String,
        id: String,
        password: String
    ) {

    }
}