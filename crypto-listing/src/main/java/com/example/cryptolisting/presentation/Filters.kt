package com.example.cryptolisting.presentation

import androidx.annotation.StringRes

enum class Filters(@StringRes val text: Int, val value: String) {
    RANK(com.example.common_ui.R.string.by_rank,"id ASC"),
    PRICE_INC(com.example.common_ui.R.string.by_price_ascending,"price ASC"),
    PRICE_DEC(com.example.common_ui.R.string.by_price_descending,"price DESC"),
    PERCENTAGE_INC(com.example.common_ui.R.string.by_percentage_ascending,"percentage ASC"),
    PERCENTAGE_DEC(com.example.common_ui.R.string.by_percentage_descending,"percentage DESC"),
    NAME(com.example.common_ui.R.string.by_name,"name ASC")
}