package com.scoreplace.hanple.ui.onboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnboardViewModel: ViewModel() {
    //Privacy Policy 스크롤 감지
    private val _scrolled = MutableLiveData<Boolean>(false)
    val scrolled: LiveData<Boolean> get() = _scrolled

    fun updateReadAll(isChecked: Boolean) {
        _scrolled.value = isChecked
    }
}