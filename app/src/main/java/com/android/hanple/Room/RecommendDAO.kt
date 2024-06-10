package com.android.hanple.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RecommendDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)  // INSERT, key 충돌이 나면 새 데이터로 교체
    suspend fun insertRecommendPlace(place: RecommendPlace)


    @Query("SELECT * FROM recommend_table WHERE recommend_id = :id")
    suspend fun getRecommendPlaceById(id: Int): RecommendPlace

    @Query("SELECT * FROM recommend_table")
    suspend fun getTotalRecommendPlaceById(): List<RecommendPlace>
    @Query("DELETE FROM recommend_table")
    suspend fun deleteItem()

}