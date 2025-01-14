package com.example.core.data.db.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.db.entities.CryptoListingEntity
import kotlinx.coroutines.flow.Flow

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

    @Query("""
        SELECT * 
        FROM CryptoListingEntity 
        WHERE id IN (SELECT id FROM WalletCryptoEntity)
    """)
    suspend fun getCryptoListingsInWallet(): List<CryptoListingEntity>


    @Query("""
        SELECT * 
        FROM CryptoListingEntity 
        WHERE cryptoId IN (SELECT crypto FROM FavouriteEntity)
    """)
    fun getCryptoListingsInFavorites(): Flow<List<CryptoListingEntity>>


    @Query("""
        SELECT * 
        FROM CryptoListingEntity 
        WHERE cryptoId = :cryptoId
    """)
    suspend fun getCryptoListingById(cryptoId : String): CryptoListingEntity?

    // Запрос для получения всех имен в виде списка строк
    @Query("SELECT name FROM CryptoListingEntity")
    suspend fun getAllNames(): List<String>

    // Запрос для получения объекта по имени
    @Query("SELECT * FROM CryptoListingEntity WHERE name = :name LIMIT 1")
    suspend fun getCryptoByName(name: String): CryptoListingEntity?
}