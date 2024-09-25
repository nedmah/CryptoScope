package com.example.core.util

fun calculatePercentageChange(prices: List<Float>): Float {
    if (prices.isEmpty()) {
        throw IllegalArgumentException("The price list cannot be empty.")
    }

    val minPrice = prices.minOrNull() ?: return 0f
    val maxPrice = prices.maxOrNull() ?: return 0f

    val firstMinIndex = prices.indexOf(minPrice)
    val firstMaxIndex = prices.indexOf(maxPrice)

    return if (firstMinIndex < firstMaxIndex) {
        // Если минимальный элемент был раньше максимального — считаем рост
        ((maxPrice - minPrice) / minPrice) * 100
    } else {
        // Если максимальный элемент был раньше минимального — считаем падение
        ((minPrice - maxPrice) / maxPrice) * 100
    }
}