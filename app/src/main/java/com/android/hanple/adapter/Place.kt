package com.android.hanple.adapter

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import android.net.Uri
import com.google.android.libraries.places.api.model.OpeningHours

data class CategoryPlace(
    val address: String?,
    val score: Double?,
    var img: Uri?,
    val id: String?,
    val name: String?,
    var isFavorite: Boolean,
    val openingHours: OpeningHours?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readParcelable(Bitmap::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(OpeningHours::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeValue(score)
        parcel.writeParcelable(img, flags)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeParcelable(openingHours, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryPlace> {
        override fun createFromParcel(parcel: Parcel): CategoryPlace {
            return CategoryPlace(parcel)
        }

        override fun newArray(size: Int): Array<CategoryPlace?> {
            return arrayOfNulls(size)
        }
    }

    fun setImgUri(uri: Uri) {
        this.img = uri
    }
}
