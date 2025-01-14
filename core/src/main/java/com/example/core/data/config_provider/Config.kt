package com.example.core.data.config_provider

data class Config(
    val themeType: ThemeType,
    val currency : String,
    val currencyRate : Double,
    val selectedWallet : String,
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
