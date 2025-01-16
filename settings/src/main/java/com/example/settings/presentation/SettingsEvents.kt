package com.example.settings.presentation

sealed class SettingsEvents {
    data object ChangeTheme : SettingsEvents()
    data object ChangeCurrency : SettingsEvents()
    data object ChangeLanguage : SettingsEvents()
    data object ChangeMainAccount : SettingsEvents()
    data object AddAccount : SettingsEvents()
}