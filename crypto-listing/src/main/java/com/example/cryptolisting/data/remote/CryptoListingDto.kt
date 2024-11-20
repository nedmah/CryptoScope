package com.example.cryptolisting.data.remote

import com.example.core.data.network.dto.CryptoListingDataDto
import com.squareup.moshi.Json


data class CryptoListingDto (

    @field:Json(name = "result")
    val listings : List<CryptoListingDataDto>
)