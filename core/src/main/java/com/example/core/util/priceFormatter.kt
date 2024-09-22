package com.example.core.util

import java.text.DecimalFormat

fun formatPrice(price: String): String {

    val numericPrice = price.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid price format")

    val decimalFormat = if (numericPrice < 0.01) {
        DecimalFormat("#,##0.#######")
    } else {
        DecimalFormat("#,##0.###")
    }

    return "$" + decimalFormat.format(numericPrice)
}