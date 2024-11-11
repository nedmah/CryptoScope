package com.example.common_ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.example.common_ui.theme.model.CryptoColor
import com.example.common_ui.theme.model.DarkChart
import com.example.common_ui.theme.model.DarkChartGradient
import com.example.common_ui.theme.model.DarkHyperlink
import com.example.common_ui.theme.model.DarkNegative
import com.example.common_ui.theme.model.DarkPercentageCard
import com.example.common_ui.theme.model.DarkPositive
import com.example.common_ui.theme.model.DarkWallet
import com.example.common_ui.theme.model.LightChart
import com.example.common_ui.theme.model.LightChartGradient
import com.example.common_ui.theme.model.LightHyperlink
import com.example.common_ui.theme.model.LightNegative
import com.example.common_ui.theme.model.LightPercentageCard
import com.example.common_ui.theme.model.LightPositive
import com.example.common_ui.theme.model.LightWallet
import com.example.common_ui.theme.model.Paddings
import com.example.common_ui.theme.model.Spacers

private val LightColors = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    inversePrimary = LightInversePrimary,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiaryContainer,
    onTertiaryContainer = LightOnTertiaryContainer,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    surfaceTint = LightSurfaceTint,
    inverseSurface = LightInverseSurface,
    inverseOnSurface = LightInverseOnSurface,
    error = LightError,
    onError = LightOnError,
    errorContainer = LightErrorContainer,
    onErrorContainer = LightOnErrorContainer,
    outline = LightOutline,
    outlineVariant = LightOutlineVariant,
    scrim = LightScrim
)

private val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    inversePrimary = DarkInversePrimary,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    surfaceTint = DarkSurfaceTint,
    inverseSurface = DarkInverseSurface,
    inverseOnSurface = DarkInverseOnSurface,
    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant,
    scrim = DarkScrim
)


private val darkExtraColor = CryptoColor(
    positive = DarkPositive,
    negative = DarkNegative,
    chart = DarkChart,
    wallet = DarkWallet,
    chartGradient = DarkChartGradient,
    percentageCard = LightPercentageCard,
    hyperlink = DarkHyperlink
)

private val lightExtraColor = CryptoColor(
    positive = LightPositive,
    negative = LightNegative,
    chart = LightChart,
    wallet = LightWallet,
    chartGradient = LightChartGradient,
    percentageCard = DarkPercentageCard,
    hyperlink = LightHyperlink
)

private val LocalExtraColor = staticCompositionLocalOf<CryptoColor> {
    error("CompositionLocal LocalExtraColor not present")
}

private val LocalPaddings = staticCompositionLocalOf<Paddings> {
    error("CompositionLocal LocalDimensions not present")
}

private val LocalSpacers = staticCompositionLocalOf<Spacers> {
    error("CompositionLocal LocalSpacers not present")
}

/**
 * extraSmall = 4.dp
 * small = 8.dp
 * extraMedium = 12.dp
 * medium = 16.dp
 * large = 24.dp
 * extraLarge = 32.dp
 * xxLarge = 64.dp
 */
val MaterialTheme.paddings: Paddings
    @Composable
    @ReadOnlyComposable
    get() = LocalPaddings.current

val MaterialTheme.spacers: Spacers
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacers.current

val MaterialTheme.extraColor: CryptoColor
    @Composable
    @ReadOnlyComposable
    get() = LocalExtraColor.current

@Composable
fun CryptoScopeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }

    val extraColors = if (darkTheme) darkExtraColor else lightExtraColor

    CompositionLocalProvider(
        LocalPaddings provides Paddings,
        LocalSpacers provides Spacers,
        LocalExtraColor provides extraColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }

}