package com.scoreplace.hanple.data.database

import android.content.Context


class BookMarkSharedPrefs(context : Context) {
    private val sharedPrefs = context.getSharedPreferences("favorite_places", Context.MODE_PRIVATE)

    fun getPlaceCount(key : String) : Int {
        return sharedPrefs.getInt(key, 0)
    }
}