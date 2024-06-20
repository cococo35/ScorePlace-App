package com.scoreplace.hanple.ui.onboarding

import android.util.MutableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrivatePolicyViewModel: ViewModel() {
    private val _readAll = MutableLiveData<Boolean>(false)
    val readAll: LiveData<Boolean> get() = _readAll

    fun updateReadAll(isChecked: Boolean) {
        _readAll.value = isChecked
    }

}
