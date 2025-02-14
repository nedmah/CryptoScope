package com.example.core.data.db.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.db.entities.WalletChartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletChartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWalletChartData(data : List<WalletChartEntity>)

    @Query("DELETE FROM walletchartentity")
    suspend fun clearWalletChartData()

    @Query("SELECT * FROM walletchartentity")
    suspend fun getAllData() : List<WalletChartEntity>

    @Query("SELECT * FROM walletchartentity ORDER BY timestamp DESC LIMIT 2")
    fun getLastTwoEntries(): Flow<List<WalletChartEntity>>

    @Query("SELECT * FROM walletchartentity ORDER BY timestamp DESC")
    fun getPagedData(): PagingSource<Int, WalletChartEntity>

}