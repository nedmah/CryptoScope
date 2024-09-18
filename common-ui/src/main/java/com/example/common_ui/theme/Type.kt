package com.example.common_ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.common_ui.R


private val Inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_extrabold, FontWeight.ExtraBold),
    Font(R.font.inter_extralight, FontWeight.ExtraLight),
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_thin, FontWeight.Thin),
    Font(R.font.inter_black, FontWeight.Black),
)
private const val FontFeatureSettings = "ss01, cv05, cv10"
private const val FontFeatureSettingsWithUpperCase = "$FontFeatureSettings, smcp"


// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.sp,
        lineHeight = 88.sp,
        fontSize = 80.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.sp,
        lineHeight = 56.sp,
        fontSize = 48.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.sp,
        lineHeight = 44.sp,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.sp,
        lineHeight = 40.sp,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.sp,
        lineHeight = 36.sp,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.sp,
        lineHeight = 32.sp,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.sp,
        lineHeight = 28.sp,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W600,
        letterSpacing = 0.1.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.sp,
        lineHeight = 16.sp,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.04.em,
        lineHeight = 12.sp,
        fontSize = 10.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 24.sp,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettingsWithUpperCase,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontFeatureSettings = FontFeatureSettings,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 16.sp,
        fontSize = 12.sp
    ),
)