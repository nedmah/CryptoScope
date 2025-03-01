package com.example.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun formatTimestamp(timestamp: Long, simple: Boolean = false): String {

    val pattern = if (simple) "dd.MM.yyyy" else "dd.MM.yyyy HH:mm"
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()  // Adjust timezone as needed

    // Определяем, в миллисекундах или секундах
    val isMilli = timestamp > 9999999999L // Если больше 10 цифр, считаем миллисекунды
    val date = if (isMilli) Date(timestamp) else Date(timestamp * 1000)
    return sdf.format(date)  // Convert from seconds to milliseconds
}

