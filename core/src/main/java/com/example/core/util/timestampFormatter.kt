package com.example.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()  // Adjust timezone as needed
    return sdf.format(Date(timestamp * 1000))  // Convert from seconds to milliseconds
}