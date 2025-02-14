package com.example.settings.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import com.example.settings.navigation.Routes

@Immutable
data class SettingsScreenState(
    val items : List<SettingsItem> = listOf(
        SettingsItem(
            com.example.common_ui.R.string.comparison,
            com.example.common_ui.R.drawable.ic_comparison_24,
            Routes.CryptoComparisonScreen.name
        ),
        SettingsItem(
            com.example.common_ui.R.string.accounts,
            com.example.common_ui.R.drawable.ic_list_24,
            Routes.CryptoAccountsScreen.name
        ),
        SettingsItem(
            com.example.common_ui.R.string.theme,
            com.example.common_ui.R.drawable.ic_lightmode_24
        ),
        SettingsItem(
            com.example.common_ui.R.string.currency,
            route = Routes.CryptoCurrencyScreen.name,
            title = "usd"
        ),
        SettingsItem(
            com.example.common_ui.R.string.language,
            route = Routes.CryptoLanguageScreen.name,
            title = "ru"
        ),
        SettingsItem(
            com.example.common_ui.R.string.info,
            com.example.common_ui.R.drawable.ic_info_24,
            Routes.CryptoAboutScreen.name
        ),
    ),

    val isDarkTheme: Boolean? = true,
    val currency: String? = "usd",
    val language: String? = "ru",

)
