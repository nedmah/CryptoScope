package com.example.crypto_info.data

import com.example.crypto_info.data.remote.CryptoInfoDto
import com.example.crypto_info.domain.model.CryptoInfo

fun CryptoInfoDto.toCryptoInfoModel() : CryptoInfo{
    return CryptoInfo(
        time = cryptoInfo.map { it.time },
        prices = cryptoInfo.map { data ->
            data.price.toFloat()
        }
    )
}