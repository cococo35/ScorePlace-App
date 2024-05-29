package com.android.hanple.repository

import com.android.hanple.data.User

// 사용자 데이터를 관리하는 클래스
object UserRepository {
    // 사용자 리스트
    private val users = listOf(
        User("test1", "test1p"),
        User("test2", "test2p")
        // 여기에 다른 사용자 추가 가능
    )

    // 사용자 유효성 검사를 위한 메서드
    fun validateUser(userId: String, password: String): Boolean {
        // 입력받은 userId와 일치하는 사용자를 리스트에서 찾는다.
        val user = users.firstOrNull { it.id == userId }
        // 사용자가 존재하고 비밀번호가 일치하는지 확인
        return user?.password == password
    }
}
