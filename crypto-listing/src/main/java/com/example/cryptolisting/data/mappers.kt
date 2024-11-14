package com.example.cryptolisting.data

import com.example.core.data.db.entities.CryptoListingEntity
import com.example.cryptolisting.data.remote.CryptoListingDataDto
import com.example.core.domain.model.CryptoListingsModel

fun CryptoListingDataDto.toCryptoEntity() : CryptoListingEntity {
    return CryptoListingEntity(
        id = rank,
        cryptoId = id,
        iconUrl = icon,
        name = name,
        symbol = symbol,
        price = price.toString(),
        percentage = percentage,
        percentageOneHour = percentageOneHour.toString(),
        percentageOneWeek = percentageOneWeek.toString(),
        totalSupply = totalSupply.toString(),
        marketCap = marketCap.toString(),
        redditUrl = redditUrl ?: "https://www.reddit.com",
        twitterUrl = twitterUrl ?: "https://twitter.com/SkyEcosystem"
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
