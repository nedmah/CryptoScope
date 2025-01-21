package com.example.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BlockchainsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val connectionId : String,
    val name : String,
    val icon : String,
    val chain : String
)
