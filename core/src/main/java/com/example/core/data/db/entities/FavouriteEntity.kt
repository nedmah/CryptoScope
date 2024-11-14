package com.example.core.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity()
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val crypto: String  //cryptoId
)
