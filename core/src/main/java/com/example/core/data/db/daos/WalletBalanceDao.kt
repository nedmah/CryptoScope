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
}