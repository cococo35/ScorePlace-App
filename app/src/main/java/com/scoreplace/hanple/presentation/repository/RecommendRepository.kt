package com.scoreplace.hanple.presentation.repository

import android.content.Context
import androidx.room.Room
import com.scoreplace.hanple.data.database.room.RecommendCacheDataResource
import com.scoreplace.hanple.room.RecommendDAO
import com.scoreplace.hanple.room.RecommendDataBase

// 추후 수정 예정
//interface RecommendRepository {
//    fun provideStudentDataBase(
//        context: Context
//    ) = RecommendCacheDataResource.provideStudentDataBase(context)
//
//
//    fun provideStudentDao(dataBase: RecommendDataBase) = RecommendCacheDataResource.provideStudentDao(provideStudentDataBase())
//}