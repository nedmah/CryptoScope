package com.example.core.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.core.data.db.entities.BlockchainsEntity

@Dao
interface BlockchainsDao {

    @Upsert
    suspend fun upsertAll(blockChains: List<BlockchainsEntity>)

    @Query("SELECT * FROM blockchainsentity")
    suspend fun getAllData() : List<BlockchainsEntity>

    @Query("SELECT * FROM blockchainsentity WHERE name = :name")
    suspend fun getBlockchainByName(name : String) : BlockchainsEntity

    @Query("SELECT * FROM blockchainsentity WHERE connectionId = :id")
    suspend fun getBlockchainById(id : String) : BlockchainsEntity


    @Query("DELETE FROM blockchainsentity")
    suspend fun clearAllData()
}