package com.example.core.util

fun truncateText(text: String, maxLength: Int): String {
    return if (text.length > maxLength) {
        text.take(maxLength - 3) + "..."
    } else {
        text
    }
}
