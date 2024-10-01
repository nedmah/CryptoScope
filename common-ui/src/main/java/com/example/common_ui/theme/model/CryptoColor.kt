package com.example.common_ui.theme.model

import androidx.compose.ui.graphics.Color


/* Custom light colors */
internal val LightPositive = Color(0xFF12AE62)
internal val LightNegative = Color(0xFFF15950)
internal val LightChart = Color(0xFF00BDB0)
internal val LightChartGradient = Color(0x3300BDB0)
internal val LightPercentageCard = Color(0xFFBCF7DB)



/* Custom dark colors */
internal val DarkPositive = Color(0xFF18DD7D)
internal val DarkNegative = Color(0xFFF15950)
internal val DarkChart = Color(0xFF00BDB0)
internal val DarkChartGradient = Color(0x3300BDB0)
internal val DarkPercentageCard = Color(0xFFDBFBEC)


class CryptoColor (
    val positive : Color,
    val negative : Color,
    val chart : Color,
    val chartGradient : Color,
    val percentageCard : Color
    )