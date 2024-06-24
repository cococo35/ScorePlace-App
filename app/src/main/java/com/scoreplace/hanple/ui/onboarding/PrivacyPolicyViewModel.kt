package com.scoreplace.hanple.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrivacyPolicyViewModel: ViewModel() {
    private val _scrolled = MutableLiveData<Boolean>(false)
    val scrolled: LiveData<Boolean> get() = _scrolled

    private val isChecked: Boolean = false

    fun updateScrolled(isScrolled: Boolean) {
        _scrolled.value = isScrolled
    }
}
