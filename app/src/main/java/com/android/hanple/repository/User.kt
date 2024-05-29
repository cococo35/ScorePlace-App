package com.android.hanple.repository

import com.android.hanple.data.User

object UserRepository {
    private val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun getUserById(userId: String): User? {
        return users.firstOrNull { it.id == userId }
    }


    // 사용자 유효성 검사를 위한 메서드
    fun validateUser(userId: String, password: String): Boolean {
        val user = getUserById(userId)
        return user?.password == password
    }
}
