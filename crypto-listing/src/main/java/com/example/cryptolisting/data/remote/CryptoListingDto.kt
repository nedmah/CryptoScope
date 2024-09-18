package com.example.cryptolisting.data.remote

import com.squareup.moshi.Json


data class CryptoListingDto (

    @field:Json(name = "result")
    val listings : List<CryptoListingDataDto>
)