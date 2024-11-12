package com.example.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class WalletBalanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,  // ID автоматически генерируется
    val totalBalance: String,  // Общий баланс в фиатных деньгах
    val profitOrAddition: String,  // Профит или пополнение
    val isAddition: Boolean,  // Флаг, является ли это пополнением
    val percentageChange: String,  // Средний процент изменения активов
    val date: String  // Дата записи
)
