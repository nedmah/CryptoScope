package com.example.core.util

import java.text.DecimalFormat

fun calculatePercentageChange(prices: List<Float>): String {
    if (prices.isEmpty()) {
        throw IllegalArgumentException("The price list cannot be empty.")
    }

    val minPrice = prices.minOrNull() ?: return "0"
    val maxPrice = prices.maxOrNull() ?: return "0"

    val firstMinIndex = prices.indexOf(minPrice)
    val firstMaxIndex = prices.indexOf(maxPrice)

    val result = if (firstMinIndex < firstMaxIndex) ((maxPrice - minPrice) / minPrice) * 100
    else ((minPrice - maxPrice) / maxPrice) * 100

    val decimalFormat = DecimalFormat("#,##0.###")

    return decimalFormat.format(result)
}