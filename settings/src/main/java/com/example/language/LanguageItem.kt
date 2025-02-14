package com.example.language

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class LanguageItem(
    @StringRes val locale : Int,
    @StringRes val description : Int,
    @DrawableRes val imageId : Int,
)
