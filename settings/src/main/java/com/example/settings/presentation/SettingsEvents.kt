package com.example.settings.presentation

sealed class SettingsEvents {
    data object ChangeTheme : SettingsEvents()
    data class AddApiKey(val api : String) : SettingsEvents()
    data object DeleteApiKey : SettingsEvents()
}