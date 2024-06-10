package com.android.hanple.adapter

import android.graphics.Bitmap
import android.net.Uri
import com.google.android.libraries.places.api.model.OpeningHours

class CategoryPlace(
    val address: String?,
    val score: Double?,
    var img: Uri?,
    val id: String?,
    val name: String?,
    val isFavorite: Boolean = false,
    val openingHours: OpeningHours?,
) {
    fun setImgUri(uri: Uri?){
        this.img = uri
    }
}
