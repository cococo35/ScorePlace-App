package com.android.hanple.data

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.OpeningHours

data class CategoryPlace(
    val address: String?,
    val score: Double?,
    var img: Uri?,
    val id: String?,
    val name: String?,
    val latLng: LatLng?,
    val priceLevel: Int?,
    val description: String?,
    var isFavorite: Boolean,
    val openingHours: OpeningHours?,

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readParcelable(Uri::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(LatLng::class.java.classLoader),
        parcel.readInt(),
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
        parcel.writeParcelable(latLng, flags)
        parcel.writeInt(priceLevel!!)
        parcel.writeString(description)
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

    fun setImgUri(uri: Uri?){
        this.img = uri
    }
}