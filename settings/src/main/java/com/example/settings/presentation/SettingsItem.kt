package com.example.settings.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


data class SettingsItem(
    @StringRes val nameId : Int,
    @DrawableRes val imageId : Int? = null,
    val route : String? = null,
    val title : String? = null  //if item don't need an image
)
