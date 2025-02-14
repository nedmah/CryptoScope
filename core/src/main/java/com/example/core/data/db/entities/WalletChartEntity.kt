package com.example.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WalletChartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp : Long,
    val price : Double
)
