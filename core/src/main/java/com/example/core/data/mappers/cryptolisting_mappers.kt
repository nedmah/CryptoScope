package com.example.core.data.mappers

import com.example.core.data.db.entities.CryptoListingEntity
import com.example.core.data.network.dto.CryptoListingDataDto
import com.example.core.domain.model.CryptoListingsModel
import com.example.core.util.formatPriceWithCurrency


fun CryptoListingEntity.toCryptoListingsModel(currencyCode: String, currencyRate: Double) : CryptoListingsModel {
    return CryptoListingsModel(
        rank = id,
        symbol = symbol,
        name = name,
        icon = iconUrl,
        price = formatPriceWithCurrency(price.toDouble(), currencyCode, currencyRate),
        percentage = percentage,
        cryptoId = cryptoId,
        percentageOneHour = percentageOneHour,
        percentageOneWeek = percentageOneWeek,
        totalSupply = formatPriceWithCurrency(totalSupply.toDouble(), currencyCode, currencyRate),
        marketCap = formatPriceWithCurrency(marketCap.toDouble(), currencyCode, currencyRate),
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

fun CryptoListingDataDto.toCryptoListingsModel() : CryptoListingsModel {
    return CryptoListingsModel(
        rank = rank,
        symbol = symbol,
        name = name,
        icon = icon,
        cryptoId = id,
        price = price,
        percentage = percentage,
        percentageOneHour = percentageOneHour.toString(),
        percentageOneWeek = percentageOneWeek.toString(),
        totalSupply = totalSupply.toString(),
        marketCap = marketCap,
        redditUrl = redditUrl ?: "https://www.reddit.com",
        twitterUrl = twitterUrl ?: "https://twitter.com/SkyEcosystem"
    )
}