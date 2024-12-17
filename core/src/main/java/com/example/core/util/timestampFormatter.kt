package com.example.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun formatTimestamp(timestamp: Long, milli: Boolean = true, simple: Boolean = false): String {

    val pattern = if (simple) "dd.MM.yyyy" else "dd.MM.yyyy HH:mm"
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()  // Adjust timezone as needed

    val date = if (milli) Date(timestamp * 1000) else Date(timestamp)
    return sdf.format(date)  // Convert from seconds to milliseconds
}

