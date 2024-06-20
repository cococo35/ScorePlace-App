package com.scoreplace.hanple.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(private val userRepository: FirebaseRepository) : ViewModel() {
    fun updateUserName(newUserName: String) {
        viewModelScope.launch {
            try {
                userRepository.updateUserName(newUserName)
                // UI 상태 업데이트 (필요한 경우)
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }

}