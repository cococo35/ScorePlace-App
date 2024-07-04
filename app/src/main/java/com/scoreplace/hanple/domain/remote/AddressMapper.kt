package com.scoreplace.hanple.domain.remote

import com.scoreplace.hanple.Address.AddressDocuments
import com.scoreplace.hanple.Address.AddressMeta
import com.scoreplace.hanple.Address.AddressResponse
import com.scoreplace.hanple.Address.SameName

fun AddressResponse.toEntity() = AddressResponseEntity(
    meta = meta?.toEntity(),
    documents = documents?.map { it ->
        it.toEntity()
    }
)

fun AddressMeta.toEntity() = AddressMetaEntity(
    total_count = total_count,
    pageable_count = pageable_count,
    is_end = is_end,
    same_name = same_name?.toEntity()
)

fun SameName.toEntity() = SameNameEntity(
    region = region,
    keyword = keyword,
    selected_region = selected_region
)

fun AddressDocuments.toEntity() = AddressDocumentsEntity(
    address_name = address_name,
    address_type = address_type,
    x = x,
    y = y
)
