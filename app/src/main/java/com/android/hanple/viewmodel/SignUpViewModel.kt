package com.android.hanple.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hanple.data.User
import com.android.hanple.repository.UserRepository

class SignupViewModel : ViewModel() {
    private val _signupData = MutableLiveData(SignupData())
    val signupData: LiveData<SignupData> = _signupData

    private val _signupSuccess = MutableLiveData<Boolean>()
    val signupSuccess: LiveData<Boolean> = _signupSuccess

    fun updateSignupData(field: Field, value: String) {
        val currentData = _signupData.value ?: SignupData()
        val updatedData = when (field) {
            Field.NAME -> currentData.copy(name = value)
            Field.ID -> currentData.copy(id = value)
            Field.PASSWORD -> currentData.copy(password = value)
            Field.EMAIL -> if (emailRegex.containsMatchIn(value)) currentData.copy(address = value) else currentData.copy(address = null)
            Field.PHONE -> if (phoneNumberRegex.containsMatchIn(value)) currentData.copy(phoneNumber = value) else currentData.copy(phoneNumber = null)
        }
        _signupData.value = updatedData
    }

    fun checkStatus(): Boolean {
        return (_signupData.value?.checkStatus() == true)
    }

    fun registerUser() {
        val newUser = _signupData.value?.asUser()
        if (newUser != null) {
            UserRepository.addUser(newUser)
            _signupSuccess.value = true
        } else {
            _signupSuccess.value = false
        }
    }

    companion object {
        private val emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex()
        private val phoneNumberRegex = "^\\+?\\d{1,3}[- ]?\\d{3,}(?:[- ]?\\d{3,})?\$".toRegex()
    }

    enum class Field {
        NAME, ID, PASSWORD, EMAIL, PHONE
    }

    data class SignupData(
        val phoneNumber: String? = null,
        val address: String? = null,
        val name: String? = null,
        val id: String? = null,
        val password: String? = null,
    ) {
        fun checkStatus(): Boolean =
            (!phoneNumber.isNullOrBlank() || !address.isNullOrBlank()) && !name.isNullOrBlank() && !id.isNullOrBlank() && !password.isNullOrBlank()

        fun asUser(): User {
            return User(
                id = id ?: "",
                name = name ?: "",
                password = password ?: "",
                phoneNumber = phoneNumber,
                address = address
            )
        }
    }
}
