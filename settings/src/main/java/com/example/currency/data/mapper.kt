package com.example.currency.data

import com.example.currency.domain.CurrencyModel

fun Map<String, Double>.toCurrencyList(): List<CurrencyModel> = buildList {
    this@toCurrencyList.forEach { (code, rate) ->
        add(CurrencyModel(code = code, currency = rate))
    }
}