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
        percentage = percentage
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
        percentage = percentage
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
        cryptoId = cryptoId
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
        percentage = percentage
    )
}