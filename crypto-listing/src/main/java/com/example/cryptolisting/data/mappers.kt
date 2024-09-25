package com.example.cryptolisting.data

import com.example.cryptolisting.data.local.CryptoListingEntity
import com.example.cryptolisting.data.remote.CryptoListingDataDto
import com.example.cryptolisting.domain.model.CryptoListingsModel

fun CryptoListingDataDto.toCryptoEntity() : CryptoListingEntity{
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

fun CryptoListingDataDto.toCryptoListingsModel() : CryptoListingsModel{
    return CryptoListingsModel(
        rank = rank,
        symbol = symbol,
        name = name,
        icon = icon,
        cryptoId = id,
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

fun CryptoListingEntity.toCryptoListingsModel() : CryptoListingsModel{
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

fun CryptoListingsModel.toCryptoListingsEntity() : CryptoListingEntity{
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