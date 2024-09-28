package com.example.crypto_info.data

import com.example.crypto_info.domain.model.CryptoInfo


fun List<List<String>>.toCryptoInfo(): CryptoInfo {
    val times = mutableListOf<Long>()
    val prices = mutableListOf<Float>()

    for (entry in this) {
        if (entry.size >= 2) {
            // Предполагаем, что первый элемент — это время, второй — цена
            times.add(entry[0].toLongOrNull() ?: 0L)  // Преобразуем строку в Long
            prices.add(entry[1].toFloatOrNull() ?: 0f)  // Преобразуем строку в Float
        }
    }

    return CryptoInfo(
        time = times,
        prices = prices
    )
}