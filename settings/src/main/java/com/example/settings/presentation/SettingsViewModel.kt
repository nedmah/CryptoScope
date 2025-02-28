package com.example.settings.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private var _settingsState = MutableStateFlow(SettingsScreenState())
    val settingsState = _settingsState.asStateFlow()

    private val _apiKey = MutableStateFlow("") // Приватный для контроля
    val apiKey = _apiKey.asStateFlow()

    init {
        _apiKey.value = getApiKey() ?: ""
        loadSettingsFlow()
    }


    fun onEvent(events: SettingsEvents) {
        when (events) {
            SettingsEvents.ChangeTheme -> changeTheme()
            is SettingsEvents.AddApiKey -> {
                if (isValidApiKey(events.api))
                    saveApiKey(events.api)
                else {
                    _apiKey.value = "Error"
                }
            }
            SettingsEvents.DeleteApiKey -> deleteApiKey()
        }
    }

    private fun loadSettingsFlow() {
        viewModelScope.launch {
            combine(
                settingsDataStore.getBooleanFlow(SettingsConstants.THEME),
                settingsDataStore.getStringFlow(SettingsConstants.CURRENCY),
                settingsDataStore.getStringFlow(SettingsConstants.LANGUAGE)
            ) { theme, currency, language ->

                SettingsScreenState(
                    isDarkTheme = theme ?: true,
                    currency = currency ?: "usd",
                    language = language ?: "ru",
                    items = updateItemsWithTitles(theme ?: true, currency ?: "usd", language ?: "ru")
                )
            }.collect { updatedState ->
                _settingsState.value = updatedState
            }
        }
    }

    private fun changeTheme() {
        viewModelScope.launch {
                val newIsDarkTheme = when (_settingsState.value.isDarkTheme) {
                    true -> false
                    false -> true
                    null -> true
                }
                settingsDataStore.putBoolean(SettingsConstants.THEME, newIsDarkTheme)
        }
    }

    private fun getApiKey(): String? {
        return sharedPreferences.getString(SettingsConstants.API_KEY, null)
    }

    private fun saveApiKey(newKey: String) {
        sharedPreferences.edit().putString(SettingsConstants.API_KEY, newKey).apply()
        _apiKey.value = newKey
    }

    private fun deleteApiKey() {
        sharedPreferences.edit().remove(SettingsConstants.API_KEY).apply()
        _apiKey.value = ""
    }

    private fun updateItemsWithTitles(isDarkTheme: Boolean, currency: String, language: String): List<SettingsItem> {
        return _settingsState.value.items.map { item ->
            when (item.nameId) {
                com.example.common_ui.R.string.theme -> {
                    val newImageId = if (isDarkTheme) {
                        com.example.common_ui.R.drawable.ic_darkmode_24
                    } else {
                        com.example.common_ui.R.drawable.ic_lightmode_24
                    }
                    item.copy(imageId = newImageId)
                }
                com.example.common_ui.R.string.currency -> {
                    item.copy(title = currency.uppercase()) // Обновляем `title` с текущей валютой
                }
                com.example.common_ui.R.string.language -> {
                    item.copy(title = language.uppercase()) // Обновляем `title` с текущим языком
                }
                else -> item // Остальные элементы остаются без изменений
            }
        }
    }

    private fun isValidApiKey(key: String): Boolean {
        if (key.isBlank()) return false

        val base64Pattern = "^[A-Za-z0-9+/]*={0,2}$".toRegex()
        return key.matches(base64Pattern) &&
                key.length >= 40
    }
}