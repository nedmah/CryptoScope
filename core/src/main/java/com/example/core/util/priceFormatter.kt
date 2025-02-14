package com.example.core.util

import java.text.DecimalFormat

fun formatPriceString(price: String): String {

    if (price.isBlank() || price == "0") {
        return "$0.00"
    }

    val numericPrice = price.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid price format $price")

    val decimalFormat = if (numericPrice < 0.01) {
        DecimalFormat("#,###,##0.#######")
    } else if (numericPrice > 0.9 && numericPrice < 1.1){
        DecimalFormat("#,###,##0.######")
    }
    else {
        DecimalFormat("#,###,##0.###")
    }

    return "$" + decimalFormat.format(numericPrice)
}

fun formatChartPrice(price: String): String {

    val floatPrice = price.toFloat()
    // Форматирование в зависимости от значения
    val decimalFormat = if (floatPrice < 0.01f) {
        DecimalFormat("#,##0.#####")
    } else {
        DecimalFormat("#,##0.###")
    }

    return decimalFormat.format(floatPrice)
}


fun formatPriceWithCurrency(priceInUsd: Double, currencyCode: String, currencyRate: Double): String {
    val convertedPrice = priceInUsd * currencyRate

    val decimalFormat = when {
        convertedPrice < 0.01 -> DecimalFormat("#,###,##0.#######")
        convertedPrice in 0.9..1.1 -> DecimalFormat("#,###,##0.######")
        else -> DecimalFormat("#,###,##0.###")
    }

    val currencySymbol = getCurrencySymbol(currencyCode)
    return "$currencySymbol${decimalFormat.format(convertedPrice)}"
}


fun getCurrencySymbol(currencyCode: String): String {
    return when (currencyCode.uppercase()) {
        "USD" -> "$"
        "EUR" -> "€"
        "RUB" -> "₽"
        "GBP" -> "£"
        "JPY" -> "¥"
        "CNY" -> "¥"
        "INR" -> "₹"
        "KRW" -> "₩"
        else -> "$currencyCode "
    }
}
