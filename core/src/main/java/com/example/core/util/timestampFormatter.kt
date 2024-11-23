package com.example.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()  // Adjust timezone as needed
    return sdf.format(Date(timestamp))  // Convert from seconds to milliseconds
}

fun formatTimestampSimple(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()  // Adjust timezone as needed

    val date = Date(timestamp)
    println("Timestamp: $timestamp")
    println("Date (raw): $date")
    println("Formatted date: ${sdf.format(date)}")
    return sdf.format(Date(timestamp))  // Convert from seconds to milliseconds
}

