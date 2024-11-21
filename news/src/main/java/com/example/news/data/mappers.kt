package com.example.news.data

import com.example.core.data.db.entities.CryptoNewsEntity
import com.example.core.util.formatTimestamp
import com.example.news.data.remote.CryptoNewsDataDto
import com.example.news.data.remote.CryptoNewsDto
import com.example.news.domain.model.CryptoNewsModel

fun CryptoNewsDataDto.toCryptoNewsEntity() : CryptoNewsEntity{
    val tags = tags.reduce{ a,b -> "$a $b"}
    val coins = relatedCoins.reduce{ a,b -> "$a $b"}
    return CryptoNewsEntity(
        newsId = id,
        date = date,
        title = title,
        sourceLink = sourceLink,
        imgUrl = imgUrl,
        shareURL = shareURL,
        link = link,
        tags = tags,
        relatedCoins = coins
    )
}

fun CryptoNewsEntity.toCryptoNewsModel() : CryptoNewsModel{
    val formattedDate = formatTimestamp(date)
    val tags = tags.split(" ").map { it.trim() }
    val coins = relatedCoins.split(" ").map { it.trim() }
    return CryptoNewsModel(
        newsId = newsId,
        date = formattedDate,
        title = title,
        sourceLink = sourceLink,
        imgUrl = imgUrl,
        shareURL = shareURL,
        link = link,
        tags = tags,
        relatedCoins = coins
    )
}