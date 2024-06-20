package com.scoreplace.hanple.ui.onboarding

import android.provider.Settings.Global.getString
import android.util.MutableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hanple.R

class PrivatePolicyViewModel: ViewModel() {
    private val _readAll = MutableLiveData<Boolean>(false)
    val readAll: LiveData<Boolean> get() = _readAll

    private val _buttonText = MutableLiveData<String>()
    val buttonText: LiveData<String> = _buttonText


    fun updateReadAll(isChecked: Boolean) {
        _readAll.value = isChecked
//        _buttonText.value =
//            if (isChecked) getString(R.string.confirm)
//            else getString(R.string.privacy_policy_unscrolled)
    }

}
