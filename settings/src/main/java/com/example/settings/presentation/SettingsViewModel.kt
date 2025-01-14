package com.example.settings.presentation

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.settings.R
import com.example.settings.navigation.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SettingsViewModel @Inject constructor() : ViewModel() {

    private var _items = MutableStateFlow(SettingsScreenState())
    val items = _items.asStateFlow()

    init {
        // TODO: сделать в инит блоке получение выбранной валюты и отображать её.
    }


    fun changeThemeIcon() {
        _items.update { currentState ->
            val updatedItems = currentState.items.map { item ->
                if (item.nameId == com.example.common_ui.R.string.theme) {
                    // Определяем новый imageId
                    val newImageId = if (item.imageId == com.example.common_ui.R.drawable.ic_darkmode_24) {
                        com.example.common_ui.R.drawable.ic_lightmode_24
                    } else {
                        com.example.common_ui.R.drawable.ic_darkmode_24
                    }
                    // Возвращаем обновлённый элемент
                    item.copy(imageId = newImageId)
                } else {
                    item
                }
            }
            // Копируем обновлённое состояние
            currentState.copy(items = updatedItems)
        }
    }


}