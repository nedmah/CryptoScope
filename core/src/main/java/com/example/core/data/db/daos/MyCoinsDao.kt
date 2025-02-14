package com.example.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.db.entities.BlockchainsEntity
import com.example.core.data.db.entities.CryptoListingEntity
import com.example.core.data.db.entities.MyCoinsEntity

@Dao
interface MyCoinsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMyCoins(coins : List<MyCoinsEntity>)

    @Query("DELETE FROM mycoinsentity")
    suspend fun clearMyCoins()

    @Query("SELECT * FROM mycoinsentity")
    suspend fun getAllData() : List<MyCoinsEntity>

}