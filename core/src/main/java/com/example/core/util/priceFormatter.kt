package com.example.core.util

import java.text.DecimalFormat

fun formatPriceString(price: String): String {

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
