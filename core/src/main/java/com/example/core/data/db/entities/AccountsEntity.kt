package com.example.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val address : String,
    val blockChain : String?,
    val imageUrl : String?,
)
