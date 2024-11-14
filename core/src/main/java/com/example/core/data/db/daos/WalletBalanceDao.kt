package com.example.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.db.entities.WalletBalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletBalanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWalletBalance(walletBalance: WalletBalanceEntity)

    @Query("SELECT * FROM WalletBalanceEntity ORDER BY date DESC")
    fun getAllBalances(): Flow<List<WalletBalanceEntity>>

    @Query("""
        SELECT * 
        FROM WalletBalanceEntity 
        ORDER BY date DESC 
        LIMIT 1
    """)
    fun getLatestWalletBalance(): WalletBalanceEntity?


    @Query("""
        SELECT COUNT(*) 
        FROM WalletBalanceEntity 
        WHERE totalBalance = :totalBalance AND profitOrAddition = :profitOrAddition
    """)
    suspend fun countMatchingRecords(totalBalance: String, profitOrAddition: String): Int
}