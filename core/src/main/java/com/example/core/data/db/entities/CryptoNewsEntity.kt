package com.example.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CryptoNewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val newsId : String,
    val date: Long,
    val title: String,
    val sourceLink: String,
    val imgUrl: String,
    val shareURL: String,
    val link: String,
    val tags: String,
    val relatedCoins: String
)
