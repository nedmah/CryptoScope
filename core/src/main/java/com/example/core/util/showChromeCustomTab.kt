package com.example.core.util

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.example.core.R

fun openCustomTab(context: Context, url: String) {
    try {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setColorScheme(CustomTabsIntent.COLOR_SCHEME_SYSTEM)
            .build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    } catch (e: Exception) {
        Toast.makeText(context, "Cannot open the link", Toast.LENGTH_SHORT).show()
    }
}