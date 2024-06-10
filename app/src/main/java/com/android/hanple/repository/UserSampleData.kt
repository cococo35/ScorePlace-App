//package com.android.hanple.repository
//
//import com.android.hanple.data.User
//
//object UserSampleData {
//    private val users = mutableListOf<User>()
//
//    init {
//        // 예시 사용자 추가
//        users.add(User("user123", "홍길동", "password123", "+821012345678", "example@example.com"))
//    }
//
//    fun addUser(user: User) {
//        users.add(user)
//    }
//
//    fun getUserById(userId: String): User? {
//        return users.firstOrNull { it.id == userId }
//    }
//
//    fun validateUser(userId: String, password: String): Boolean {
//        val user = getUserById(userId)
//        return user?.password == password
//    }
//}
