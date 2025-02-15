package com.example.core.data.db.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.core.data.db.entities.CryptoNewsEntity

@Dao
interface CryptoNewsDao {

    @Upsert
    suspend fun upsertAll(news: List<CryptoNewsEntity>)

    @Query("SELECT * FROM cryptonewsentity")
    fun pagingSource() : PagingSource<Int, CryptoNewsEntity>

    @Query("SELECT COUNT(*) FROM cryptonewsentity")
    suspend fun getCount() : Int

    @Query("DELETE FROM cryptonewsentity")
    suspend fun clearAllData()

    @Query("DELETE FROM sqlite_sequence WHERE name='cryptonewsentity'")  //for id not to increase a lot (paging logic depends on id)
    suspend fun resetAutoIncrement()

}