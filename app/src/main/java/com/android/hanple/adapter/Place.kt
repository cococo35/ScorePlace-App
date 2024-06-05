package com.android.hanple.adapter

import android.graphics.Bitmap
import com.google.android.libraries.places.api.model.OpeningHours

class CategoryPlace(
    val address: String?,
    val score: Double?,
    var img: Bitmap?,
    val id: String?,
    val name: String?,
    val isFavorite: Boolean,
    val openingHours: OpeningHours?,
) {
    fun setImgBitmap(bitmap: Bitmap){
        this.img = bitmap
    }
}
