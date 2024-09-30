package com.example.cryptolisting.presentation

enum class Filters(val text : String) {
    RANK("By rank"),
    PRICE_INC("By price ascending"),
    PRICE_DEC("By price descending"),
    PERCENTAGE_INC("By percentage ascending"),
    PERCENTAGE_DEC("By percentage descending"),
    NAME("By name")
}