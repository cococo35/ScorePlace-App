package com.scoreplace.hanple.data.database.room

import android.content.Context
import androidx.room.Room
import com.scoreplace.hanple.room.RecommendDAO
import com.scoreplace.hanple.room.RecommendDataBase


object RecommendCacheDataResource {

    fun provideStudentDataBase(
        context: Context
    ) : RecommendDataBase = Room.databaseBuilder(
        context, RecommendDataBase::class.java, "RecommendPlaceList"
    ).build()


    fun provideStudentDao(dataBase: RecommendDataBase) : RecommendDAO = dataBase.getMyRecommendPlaceDAO()
}