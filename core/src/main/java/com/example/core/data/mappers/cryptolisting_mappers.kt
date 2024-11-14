package com.example.core.data.mappers

import com.example.core.data.db.entities.CryptoListingEntity
import com.example.core.domain.model.CryptoListingsModel



fun CryptoListingEntity.toCryptoListingsModel() : CryptoListingsModel {
    return CryptoListingsModel(
        rank = id,
        symbol = symbol,
        name = name,
        icon = iconUrl,
        price = price,
        percentage = percentage,
        cryptoId = cryptoId,
        percentageOneHour = percentageOneHour,
        percentageOneWeek = percentageOneWeek,
        totalSupply = totalSupply,
        marketCap = marketCap,
        redditUrl = redditUrl,
        twitterUrl = twitterUrl
    )
}

fun CryptoListingsModel.toCryptoListingsEntity() : CryptoListingEntity {
    return CryptoListingEntity(
        id = rank,
        cryptoId = cryptoId,
        iconUrl = icon,
        name = name,
        symbol = symbol,
        price = price,
        percentage = percentage,
        percentageOneHour = percentageOneHour,
        percentageOneWeek = percentageOneWeek,
        totalSupply = totalSupply,
        marketCap = marketCap,
        redditUrl = redditUrl,
        twitterUrl = twitterUrl
    )
}