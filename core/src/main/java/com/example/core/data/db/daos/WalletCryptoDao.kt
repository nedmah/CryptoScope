package com.example.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.core.data.db.entities.CryptoListingEntity
import com.example.core.data.db.entities.WalletCryptoEntity
import com.example.core.data.db.entities.WalletWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletCryptoDao {

    @Transaction
    @Query("""
        SELECT 
            w.id, 
            w.cryptoId, 
            w.amount, 
            w.count, 
            c.name,
            c.symbol,
            c.price, 
            c.percentage
        FROM WalletCryptoEntity w 
        INNER JOIN CryptoListingEntity c ON w.cryptoId = c.cryptoId
    """)
    fun getAllWalletCryptos(): Flow<List<WalletWithDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateWalletCrypto(walletCryptoEntity: WalletCryptoEntity)

    @Query("SELECT * FROM CryptoListingEntity WHERE cryptoId = :cryptoId")
    suspend fun getCryptoById(cryptoId: String): CryptoListingEntity?

    @Update
    suspend fun updateWalletCrypto(walletCryptoEntity: WalletCryptoEntity)

}