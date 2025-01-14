package com.example.settings.presentation


data class SettingsItem(
    val nameId : Int,
    val imageId : Int? = null,
    val route : String? = null,
    val title : String? = null
)
