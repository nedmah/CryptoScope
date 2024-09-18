package com.example.core.util

import java.text.DecimalFormat

fun formatPrice(price: String): String {
    // Формат с разделителем тысяч и 3 знаками после запятой
    val decimalFormat = DecimalFormat("#,##0.###")
    return "$" + decimalFormat.format(price)
}