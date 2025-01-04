package com.example.core.data.config_provider

data class Config(
    val themeType: ThemeType,
){

    enum class ThemeType {
        System,
        Dark,
        Light;

        fun isDark(): Boolean? = when (this) {
            System -> null
            Dark -> true
            Light -> false
        }
    }
}
