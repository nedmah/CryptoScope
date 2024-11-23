package com.example.news.data.remote

import com.squareup.moshi.Json

data class CryptoNewsDto(
    val result : List<CryptoNewsDataDto>
)

data class CryptoNewsDataDto(
    val id : String,

    @field:Json(name = "searchKeyWords")
    val tags : List<String>,

    val source : String,
    val feedDate : Long,
    val title : String,
    val sourceLink : String,
    val imgUrl : String,
    val shareURL : String,
    val relatedCoins : List<String>,
    val link : String,
)

