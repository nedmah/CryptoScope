package com.example.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.db.daos.AccountsDao
import com.example.core.data.db.daos.BlockchainsDao
import com.example.core.data.db.daos.CryptoListingsDao
import com.example.core.data.db.daos.CryptoNewsDao
import com.example.core.data.db.daos.FavouritesDao
import com.example.core.data.db.daos.WalletBalanceDao
import com.example.core.data.db.daos.WalletCryptoDao
import com.example.core.data.db.entities.AccountsEntity
import com.example.core.data.db.entities.BlockchainsEntity
import com.example.core.data.db.entities.CryptoListingEntity
import com.example.core.data.db.entities.CryptoNewsEntity
import com.example.core.data.db.entities.FavouriteEntity
import com.example.core.data.db.entities.WalletBalanceEntity
import com.example.core.data.db.entities.WalletCryptoEntity

@Database(
    entities = [
        CryptoListingEntity::class,
        FavouriteEntity::class,
        WalletCryptoEntity::class,
        WalletBalanceEntity::class,
        CryptoNewsEntity::class,
        BlockchainsEntity::class,
        AccountsEntity::class
    ],
    version = 1
)
abstract class CryptoDb : RoomDatabase() {
    abstract fun getCryptoListingsDao() : CryptoListingsDao
    abstract fun getFavouritesDao() : FavouritesDao
    abstract fun getWalletBalanceDao() : WalletBalanceDao
    abstract fun getWalletCryptoDao() : WalletCryptoDao
    abstract fun getCryptoNewsDao() : CryptoNewsDao
    abstract fun getBlockchainsDao() : BlockchainsDao
    abstract fun getAccountDao() : AccountsDao
}