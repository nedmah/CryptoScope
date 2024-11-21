package com.example.news.domain.model

data class CryptoNewsModel(
    val newsId : String,
    val date: String,
    val title: String,
    val sourceLink: String,
    val imgUrl: String,
    val shareURL: String,
    val link: String,
    val tags: List<String>,
    val relatedCoins: List<String>
)
