package com.example.cryptolisting.presentation

import androidx.annotation.StringRes

enum class Filters(@StringRes val text: Int) {
    RANK(com.example.common_ui.R.string.by_rank),
    PRICE_INC(com.example.common_ui.R.string.by_price_ascending),
    PRICE_DEC(com.example.common_ui.R.string.by_price_descending),
    PERCENTAGE_INC(com.example.common_ui.R.string.by_percentage_ascending),
    PERCENTAGE_DEC(com.example.common_ui.R.string.by_percentage_descending),
    NAME(com.example.common_ui.R.string.by_name)
}