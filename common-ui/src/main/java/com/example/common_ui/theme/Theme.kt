package com.example.common_ui.theme

import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.common_ui.theme.model.CryptoColor
import com.example.common_ui.theme.model.DarkChart
import com.example.common_ui.theme.model.DarkChartGradient
import com.example.common_ui.theme.model.DarkHyperlink
import com.example.common_ui.theme.model.DarkNavIcon
import com.example.common_ui.theme.model.DarkNegative
import com.example.common_ui.theme.model.DarkPercentageCard
import com.example.common_ui.theme.model.DarkPositive
import com.example.common_ui.theme.model.DarkSecondChart
import com.example.common_ui.theme.model.DarkSecondChartGradient
import com.example.common_ui.theme.model.DarkWallet
import com.example.common_ui.theme.model.LightChart
import com.example.common_ui.theme.model.LightChartGradient
import com.example.common_ui.theme.model.LightHyperlink
import com.example.common_ui.theme.model.LightNavIcon
import com.example.common_ui.theme.model.LightNegative
import com.example.common_ui.theme.model.LightPercentageCard
import com.example.common_ui.theme.model.LightPositive
import com.example.common_ui.theme.model.LightSecondChart
import com.example.common_ui.theme.model.LightSecondChartGradient
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
    secondChart = DarkSecondChart,
    wallet = DarkWallet,
    chartGradient = DarkChartGradient,
    secondGradient = DarkSecondChartGradient,
    percentageCard = LightPercentageCard,
    hyperlink = DarkHyperlink,
    navIconColor = DarkNavIcon
)

private val lightExtraColor = CryptoColor(
    positive = LightPositive,
    negative = LightNegative,
    chart = LightChart,
    secondChart = LightSecondChart,
    wallet = LightWallet,
    chartGradient = LightChartGradient,
    secondGradient = LightSecondChartGradient,
    percentageCard = DarkPercentageCard,
    hyperlink = LightHyperlink,
    navIconColor = LightNavIcon
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
fun ColorScheme.switch(darkTheme: Boolean): ColorScheme = copy(
    primary = animateColor(primary, darkTheme),
    primaryContainer = animateColor(primaryContainer, darkTheme),
    onPrimary = animateColor(onPrimary, darkTheme),
    secondary = animateColor(secondary, darkTheme),
    secondaryContainer = animateColor(secondaryContainer, darkTheme),
    onSecondary = animateColor(onSecondary, darkTheme),
    background = animateColor(background, darkTheme),
    onBackground = animateColor(onBackground, darkTheme),
    surface = animateColor(surface, darkTheme),
    onTertiary = animateColor(onTertiary, darkTheme),
    surfaceVariant = animateColor(surfaceVariant, darkTheme),
    onSurfaceVariant = animateColor(onSurfaceVariant, darkTheme),
    outline = animateColor(outline, darkTheme),
    onSurface = animateColor(onSurface, darkTheme),
    error = animateColor(error, darkTheme),
    outlineVariant = animateColor(outlineVariant, darkTheme),
    onError = animateColor(onError, darkTheme)
)

@Composable
fun CryptoColor.switch(darkTheme: Boolean): CryptoColor = copy(
    positive = animateColor(positive, darkTheme),
    negative = animateColor(negative, darkTheme),
    chart = animateColor(chart, darkTheme),
    secondChart = animateColor(secondChart, darkTheme),
    wallet = animateColor(wallet, darkTheme),
    chartGradient = animateColor(chartGradient, darkTheme),
    secondGradient = animateColor(secondGradient, darkTheme),
    percentageCard = animateColor(percentageCard, darkTheme),
    navIconColor = animateColor(navIconColor, darkTheme),
    hyperlink = animateColor(hyperlink, darkTheme)
)



@Composable
fun animateColor(targetValue: Color, darkTheme: Boolean): Color {
    return animateColorAsState(
        targetValue = targetValue,
        animationSpec = spring(stiffness = 6000f),
        label = "colorAnimation_$darkTheme" // Уникальный ключ для анимации
    ).value
}

@Composable
fun CryptoScopeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val targetColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }

    val targetExtraColors = if (darkTheme) darkExtraColor else lightExtraColor

    val colorScheme = targetColorScheme.switch(darkTheme)
    val extraColors = targetExtraColors.switch(darkTheme)

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