package com.scoreplace.hanple.data.database.room

import android.content.Context
import androidx.room.Room
import com.scoreplace.hanple.room.RecommendDAO
import com.scoreplace.hanple.room.RecommendDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RoomDBModule {

    @Singleton
    @Provides
    fun provideStudentDataBase(
        context: Context
    ) : RecommendDataBase = Room.databaseBuilder(
        context, RecommendDataBase::class.java, "RecommendPlaceList"
    ).build()

    @Singleton
    @Provides
    fun provideStudentDao(dataBase: RecommendDataBase) : RecommendDAO = dataBase.getMyRecommendPlaceDAO()
}