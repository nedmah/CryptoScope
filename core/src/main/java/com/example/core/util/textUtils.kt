package com.example.core.util

fun truncateText(text: String, maxLength: Int): String {
    return if (text.length > maxLength) {
        text.take(maxLength - 3) + "..."
    } else {
        text
    }
}

fun truncateMiddleText(input: String, hiddenLength: Int = 6): String {
    if (input.length <= hiddenLength + 3) return input // Если строка слишком короткая, не обрезаем

    val prefixLength = (input.length - hiddenLength) / 2
    val suffixStart = prefixLength + hiddenLength

    return input.take(prefixLength) + "..." + input.takeLast(input.length - suffixStart)
}
