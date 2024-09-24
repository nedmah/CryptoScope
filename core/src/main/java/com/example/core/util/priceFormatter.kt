package com.example.core.util

import java.text.DecimalFormat

fun formatPriceString(price: String): String {

    val numericPrice = price.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid price format")

    val decimalFormat = if (numericPrice < 0.01) {
        DecimalFormat("#,##0.#######")
    } else {
        DecimalFormat("#,##0.###")
    }

    return "$" + decimalFormat.format(numericPrice)
}

fun formatPriceToFloat(price: String): String {
    // Преобразование строки в Double
    val numericPrice = price.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid price format")

    // Преобразование Double во Float
    val floatPrice = numericPrice.toFloat()

    // Форматирование в зависимости от значения
    val decimalFormat = if (floatPrice < 0.01f) {
        DecimalFormat("#,##0.#######")
    } else {
        DecimalFormat("#,##0.###")
    }

    return "$" + decimalFormat.format(floatPrice)
}
