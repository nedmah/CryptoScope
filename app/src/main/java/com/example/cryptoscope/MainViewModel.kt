package com.example.cryptoscope

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import com.example.crypto_info.presentation.crypto_info.CryptoInfoViewModel
import com.example.cryptoscope.di.viewmodel.MultiViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val settingsDataStore: SettingsDataStore,
    val factory: MultiViewModelFactory,
    val cryptoInfoViewModelFactory: CryptoInfoViewModel.CryptoInfoViewModelFactory.Factory
): ViewModel() {

    private val _themeFlow = MutableStateFlow<Boolean?>(null) // изначально null чтобы избежать моргания
    val themeFlow: StateFlow<Boolean?> = _themeFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val initialTheme = settingsDataStore.getBooleanFlow(SettingsConstants.THEME).first() ?: true
            _themeFlow.value = initialTheme

            settingsDataStore.getBooleanFlow(SettingsConstants.THEME).collectLatest { theme ->
                _themeFlow.value = theme
            }
        }
    }
}