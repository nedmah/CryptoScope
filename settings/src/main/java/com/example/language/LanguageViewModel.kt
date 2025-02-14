package com.example.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private var _selectedLanguage = MutableStateFlow("")
    val selectedLanguage = _selectedLanguage.asStateFlow()

    init {
        loadCurrentLanguage()
    }

    private fun loadCurrentLanguage() {
        viewModelScope.launch {
            settingsDataStore.getStringFlow(SettingsConstants.LANGUAGE).collect { language ->
                _selectedLanguage.value = language ?: "ru"
            }
        }
    }

    fun changeLanguage(language: String) {
        viewModelScope.launch {
            settingsDataStore.putString(SettingsConstants.LANGUAGE, language)
            _selectedLanguage.value = language
        }
    }
}