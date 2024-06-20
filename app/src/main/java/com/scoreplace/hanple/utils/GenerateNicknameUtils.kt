package com.scoreplace.hanple.utils

object GenerateNicknameUtils {
    private val adjectives = listOf( "꼼꼼한", "즐거운", "느긋한", "신나는", "배고픈", "발빠른")
    private val nouns = listOf( "해치", "고양이", "강아지", "판다", "사자")

    fun generateNickname(): String {
        val adjective = adjectives.random()
        val nouns = nouns.random()
        return "$adjective$nouns"
    }
}