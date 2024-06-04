package com.android.hanple.ui.onboarding

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hanple.data.User
import com.android.hanple.repository.UserSampleData
import com.android.hanple.utils.ConvertUtils

class SignUpViewModel : ViewModel() {
    companion object {
        const val TAG = "SignUpActivity, SharedPreference"
    }
    private fun isEmailNew(email: String): Boolean = true
    private fun isPasswordValid(
        sharedPreferences: SharedPreferences,
        email: String,
        password: String
    ) = true
    private fun saveAuthSharedPreference(
        sharedPreferences: SharedPreferences,
        email: String,
        password: String
    ) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("VALID_EMAIL", email)
        editor.putString("VALID_PASSWORD", password)
        editor.apply() //SharedPreference에 이메일, 비밀번호 저장
    }

    private val _signupData = MutableLiveData(SignupData())
    val signupData: LiveData<SignupData> = _signupData

    private val _signupSuccess = MutableLiveData<Boolean>()
    val signupSuccess: LiveData<Boolean> = _signupSuccess

    //View가 ViewModel을 관찰한다면, ViewModel은 View에게 데이터 변경, 즉 UI 내용 변경을 알려준다.
    fun updateSignupData(field: Field, value: String) { //UI update. input이 들어올 때마다 갱신
        val currentData = _signupData.value ?: SignupData()
        val updatedData = when (field) {
            Field.NAME -> currentData.copy(name = value)
            Field.ID -> currentData.copy(id = value)
            Field.PASSWORD -> currentData.copy(password = value)
            Field.PHONE -> if (ConvertUtils.phoneNumberRegex.containsMatchIn(value)) currentData.copy(
                phoneNumber = value
            ) else currentData.copy(phoneNumber = null)
        }
        _signupData.value = updatedData
    }

    fun checkStatus(): Boolean { //
        return (_signupData.value?.checkStatus() == true)
    }

    fun registerUser() {
        val newUser = _signupData.value?.asUser()
        if (newUser != null) {
            UserSampleData.addUser(newUser)
            _signupSuccess.value = true
        } else {
            _signupSuccess.value = false
        }
    }


    enum class Field {
        NAME, ID, PASSWORD, PHONE
    }

    data class SignupData(
        val phoneNumber: String? = null,
        val name: String? = null,
        val id: String? = null,
        val password: String? = null,
    ) {
        fun checkStatus(): Boolean = //null 체크.
            !phoneNumber.isNullOrBlank() && !name.isNullOrBlank() && !id.isNullOrBlank() && !password.isNullOrBlank()

        fun asUser(): User {
            return User(
                id = id ?: "",
                name = name ?: "",
                password = password ?: "",
                phoneNumber = phoneNumber ?: ""
            )
        }
    }
}

