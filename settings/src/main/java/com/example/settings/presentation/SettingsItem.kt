package com.example.settings.presentation


data class SettingsItem(
    val name : String,
    val imageId : Int? = null,
    val route : String? = null,
    val title : String? = null
)
