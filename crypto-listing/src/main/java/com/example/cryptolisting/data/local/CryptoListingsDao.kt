package com.example.cryptolisting.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CryptoListingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCryptoListings(cryptoListingEntities : List<CryptoListingEntity>)


    @Query("DELETE FROM cryptolistingentity")
    suspend fun clearListings()

    @Query("""
            SELECT * 
            FROM cryptolistingentity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
                UPPER(:query) == symbol
        """)
    suspend fun searchForListings(query : String) : List<CryptoListingEntity>

    @Query("SELECT * FROM cryptolistingentity")
    fun pagingSource() : PagingSource<Int, CryptoListingEntity>
}