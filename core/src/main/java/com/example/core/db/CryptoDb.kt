package com.example.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.db.daos.CryptoListingsDao
import com.example.core.db.daos.FavouritesDao
import com.example.core.db.entities.CryptoListingEntity
import com.example.core.db.entities.FavouriteEntity

@Database(
    entities = [
        CryptoListingEntity::class,
        FavouriteEntity::class
    ],
    version = 1
)
abstract class CryptoDb : RoomDatabase() {
    abstract fun getCryptoListingsDao() : CryptoListingsDao
    abstract fun getFavouritesDao() : FavouritesDao
}