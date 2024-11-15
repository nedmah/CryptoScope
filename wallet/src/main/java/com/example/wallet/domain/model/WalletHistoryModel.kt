package com.example.wallet.domain.model

data class WalletHistoryModel(
    val totalBalance: String,  // Общий баланс в фиатных деньгах
    val profitOrAddition: String,  // Профит или пополнение
    val isAddition: Boolean,  // Флаг, является ли это пополнением
    val percentageChange: String,  // Средний процент изменения активов
    val date: String  // Дата записи
)