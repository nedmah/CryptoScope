package com.example.core.util

fun isValidApiKey(key: String): Boolean {
    if (key.isBlank()) return false

    val base64Pattern = "^[A-Za-z0-9+/]*={0,2}$".toRegex()
    return key.matches(base64Pattern) &&
            key.length >= 40
}