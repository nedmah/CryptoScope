package com.example.news.data

import com.example.core.data.db.entities.CryptoNewsEntity
import com.example.core.util.formatTimestamp
import com.example.news.data.remote.CryptoNewsDataDto
import com.example.news.data.remote.CryptoNewsDto
import com.example.news.domain.model.CryptoNewsModel

fun CryptoNewsDataDto.toCryptoNewsEntity() : CryptoNewsEntity{
    val tags = if (tags.isNotEmpty()) tags.joinToString(" ") else ""
    val coins = if (relatedCoins.isNotEmpty()) relatedCoins.joinToString(" ") else ""
    return CryptoNewsEntity(
        newsId = id,
        date = feedDate,
        title = title,
        sourceLink = sourceLink,
        imgUrl = imgUrl,
        shareURL = shareURL,
        source = source,
        link = link,
        tags = tags,
        relatedCoins = coins
    )
}

fun CryptoNewsEntity.toCryptoNewsModel() : CryptoNewsModel{
    val formattedDate = formatTimestamp(date)
    val tags = tags.split(" ").filter { it.isNotBlank() }
    val coins = relatedCoins.split(" ").filter { it.isNotBlank() }
    return CryptoNewsModel(
        newsId = newsId,
        date = formattedDate,
        title = title,
        source = source,
        sourceLink = sourceLink,
        imgUrl = imgUrl,
        shareURL = shareURL,
        link = link,
        tags = tags,
        relatedCoins = coins
    )
}