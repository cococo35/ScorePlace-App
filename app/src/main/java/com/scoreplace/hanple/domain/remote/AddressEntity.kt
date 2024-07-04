package com.scoreplace.hanple.domain.remote

data class AddressResponseEntity(
    val meta : AddressMetaEntity?,
    val documents : List<AddressDocumentsEntity>?,
)

data class AddressMetaEntity(
    val total_count : Int?,
    val pageable_count : Int?,
    val is_end : Boolean?,
    val same_name : SameNameEntity?,
)
data class SameNameEntity(
    val region : List<String>?,
    val keyword : String?,
    val selected_region : String?,
)

data class AddressDocumentsEntity(
    val address_name : String?,
    val address_type : String?,
    val x : String?,
    val y : String?,
)
