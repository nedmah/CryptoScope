package com.example.wallet.data

import com.example.core.data.db.entities.MyCoinsEntity
import com.example.core.data.db.entities.WalletChartEntity
import com.example.core.util.formatPriceWithCurrency
import com.example.core.util.formatTimestamp
import com.example.wallet.data.network.dto.WalletChartDto
import com.example.wallet.domain.model.MyCoinsModel
import com.example.wallet.data.network.dto.WalletCoinDto
import com.example.wallet.domain.model.BalanceHistoryModel
import com.example.wallet.domain.model.WalletChartDataModel


fun MyCoinsEntity.toMyCoinsModel(currencyCode: String, currencyRate: Double) : MyCoinsModel {
    return MyCoinsModel(
        coinId = coinId,
        amount = amount,
        chain = chain,
        name = name,
        symbol = symbol,
        price = formatPriceWithCurrency(price.toDouble(),currencyCode, currencyRate),
        imgUrl = imgUrl,
        percentOneDay = percentOneDay,
        rank = rank,
        volume = volume
    )
}

fun WalletCoinDto.toMyCoinsEntity() : MyCoinsEntity{
    return MyCoinsEntity(
        coinId = coinId,
        amount = amount,
        chain = chain,
        name = name,
        symbol = symbol,
        price = price,
        imgUrl = imgUrl,
        percentOneDay = percentOneDay,
        rank = rank,
        volume = volume
    )
}

fun WalletChartDto.toWalletChartEntity(): List<WalletChartEntity> {
    return result.mapNotNull { entry ->
        if (entry.size >= 2) {
            val timestamp = entry[0].toLongOrNull()
            val price = entry[1].toDoubleOrNull()

            if (timestamp != null && price != null) {
                WalletChartEntity(timestamp = timestamp, price = price)
            } else {
                null // Пропускаем некорректные данные
            }
        } else {
            null // Пропускаем некорректные списки с недостаточным количеством элементов
        }
    }
}

fun List<WalletChartEntity>.toWalletChartDataModel() : WalletChartDataModel?{
    if (this.isEmpty()) {
        return null
    }
    return WalletChartDataModel(
        time = this.map { formatTimestamp(it.timestamp) },
        price = this.map { it.price.toFloat() }
    )
}

