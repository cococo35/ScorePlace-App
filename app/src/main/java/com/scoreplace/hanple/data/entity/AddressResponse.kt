package com.scoreplace.hanple.Address

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("meta") val meta : AddressMeta?,
    @SerializedName("documents") val documents : List<AddressDocuments>?,
)

data class AddressMeta(
    @SerializedName("total_count") val total_count : Int?,
    @SerializedName("pageable_count") val pageable_count : Int?,
    @SerializedName("is_end") val is_end : Boolean?,
    @SerializedName("same_name") val same_name : SameName?,
)
data class SameName(
    @SerializedName("region") val region : List<String>?,
    @SerializedName("keyword") val keyword : String?,
    @SerializedName("selected_region") val selected_region : String?,
)

data class AddressDocuments(
    @SerializedName("address_name") val address_name : String?,
    @SerializedName("address_type") val address_type : String?,
    @SerializedName("x") val x : String?,
    @SerializedName("y") val y : String?,
)


