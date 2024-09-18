package com.example.cryptolisting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CryptoListingEntity::class
    ],
    version = 1
)
abstract class CryptoDb : RoomDatabase() {
    abstract fun getCryptoListingsDao() : CryptoListingsDao
}