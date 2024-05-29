package com.android.hanple.data

data class User(
    val id: String,
    val name: String,
    val password: String,
    val phoneNumber: String? = null,
    val address: String? = null
)

