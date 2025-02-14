package com.example.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyCoinsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val coinId : String,
    val amount : Double,
    val chain : String,
    val name : String,
    val symbol : String,
    val price : String,
    val imgUrl : String,
    val percentOneDay : Double,
    val rank : Int,
    val volume : String
)
