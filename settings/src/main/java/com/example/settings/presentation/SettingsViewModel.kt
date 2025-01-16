package com.example.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private var _settingsState = MutableStateFlow(SettingsScreenState())
    val settingsState = _settingsState.asStateFlow()

    init {
        // TODO: сделать в инит блоке получение выбранной валюты и отображать её.
        loadSettingsFlow()
    }


    fun onEvent(events: SettingsEvents) {
        when (events) {
            SettingsEvents.AddAccount -> TODO()
            SettingsEvents.ChangeCurrency -> TODO()
            SettingsEvents.ChangeLanguage -> TODO()
            SettingsEvents.ChangeMainAccount -> TODO()
            SettingsEvents.ChangeTheme -> changeTheme()
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
                    language = language ?: "eng",
                    items = updateItemsWithTitles(theme ?: true, currency ?: "usd", language ?: "eng")
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


    private fun changeCurrency() {

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


}