package com.scoreplace.hanple.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [RecommendPlace::class], exportSchema = false, version = 1)
abstract class RecommendDataBase : RoomDatabase() {

    abstract fun getMyRecommendPlaceDAO() : RecommendDAO

    companion object {
        private var INSTANCE: RecommendDataBase? = null


        fun getMyRecommendPlaceDataBase(context: Context): RecommendDataBase {
            if(INSTANCE == null) {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context, RecommendDataBase::class.java, "RecommendPlaceList"
                    ).build()
                    INSTANCE = instance
                }
            }
            return INSTANCE!!
        }
    }

}