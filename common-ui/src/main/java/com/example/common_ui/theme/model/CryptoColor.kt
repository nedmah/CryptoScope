package com.example.common_ui.theme.model

import androidx.compose.ui.graphics.Color


/* Custom light colors */
internal val LightPositive = Color(0xFF12AE62)
internal val LightNegative = Color(0xFFF15950)
internal val LightChart = Color(0xFF00BDB0)
internal val LightSecondChart = Color(0xFF058BB3)
internal val LightWallet = Color(0xFF028F98)
internal val LightChartGradient = Color(0x3300BDB0)
internal val LightSecondChartGradient = Color(0x403B82F6)
internal val LightPercentageCard = Color(0xFFBCF7DB)
internal val LightHyperlink = Color(0xFF0554AA)
internal val LightNavIcon = Color(0xFF626E85)



/* Custom dark colors */
internal val DarkPositive = Color(0xFF18DD7D)
internal val DarkNegative = Color(0xFFF15950)
internal val DarkChart = Color(0xFF00BDB0)
internal val DarkSecondChart = Color(0xFF11B1E0)
internal val DarkWallet = Color(0xFF028F98)
internal val DarkChartGradient = Color(0x3300BDB0)
internal val DarkSecondChartGradient = Color(0x403B82F6)
internal val DarkPercentageCard = Color(0xFFDBFBEC)
internal val DarkHyperlink = Color(0xFF1ECEC8)
internal val DarkNavIcon = Color(0xFF626E85)

class CryptoColor (
    val positive : Color,
    val negative : Color,
    val chart : Color,
    val secondChart : Color,
    val wallet : Color,
    val chartGradient : Color,
    val secondGradient : Color,
    val percentageCard : Color,
    val navIconColor : Color,
    val hyperlink : Color
    )