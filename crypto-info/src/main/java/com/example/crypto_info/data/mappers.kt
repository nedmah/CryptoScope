package com.example.crypto_info.data

import com.example.crypto_info.data.remote.CryptoInfoDto
import com.example.crypto_info.domain.model.CryptoInfo

fun CryptoInfoDto.toCryptoInfoModel() : CryptoInfo{

    val times = cryptoInfo.flatten().map { it.time }
    val prices = cryptoInfo.flatten().map { it.price.toFloat() }
    return CryptoInfo(
        time = times,
        prices = prices
    )

}