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
    suspend fun saveCryptoListings(cryptoListingEntities: List<CryptoListingEntity>)


    @Query("DELETE FROM cryptolistingentity")
    suspend fun clearListings()

    @Query("SELECT * FROM cryptolistingentity")
    suspend fun getAllListings(): List<CryptoListingEntity>


    @Query("""
    SELECT * FROM cryptolistingentity
    WHERE name LIKE '%' || :query || '%' 
    ORDER BY id ASC
""")
    fun getPagedDataByIdAsc(query: String): PagingSource<Int, CryptoListingEntity>

    @Query("""
    SELECT * FROM cryptolistingentity
    WHERE name LIKE '%' || :query || '%' 
    ORDER BY id DESC
""")
    fun getPagedDataByIdDesc(query: String): PagingSource<Int, CryptoListingEntity>

    @Query("""
    SELECT * FROM cryptolistingentity
    WHERE name LIKE '%' || :query || '%' 
    ORDER BY price ASC
""")
    fun getPagedDataByPriceAsc(query: String): PagingSource<Int, CryptoListingEntity>

    @Query("""
    SELECT * FROM cryptolistingentity
    WHERE name LIKE '%' || :query || '%' 
    ORDER BY price DESC
""")
    fun getPagedDataByPriceDesc(query: String): PagingSource<Int, CryptoListingEntity>

    @Query("""
    SELECT * FROM cryptolistingentity
    WHERE name LIKE '%' || :query || '%' 
    ORDER BY percentage ASC
""")
    fun getPagedDataByPercentageAsc(query: String): PagingSource<Int, CryptoListingEntity>

    @Query("""
    SELECT * FROM cryptolistingentity
    WHERE name LIKE '%' || :query || '%' 
    ORDER BY percentage DESC
""")
    fun getPagedDataByPercentageDesc(query: String): PagingSource<Int, CryptoListingEntity>

    @Query("""
    SELECT * FROM cryptolistingentity
    WHERE name LIKE '%' || :query || '%' 
    ORDER BY name ASC
""")
    fun getPagedDataByNameAsc(query: String): PagingSource<Int, CryptoListingEntity>

    @Query("""
    SELECT * FROM cryptolistingentity
    WHERE name LIKE '%' || :query || '%' 
    ORDER BY name DESC
""")
    fun getPagedDataByNameDesc(query: String): PagingSource<Int, CryptoListingEntity>

    @Query("SELECT COUNT(*) FROM cryptolistingentity WHERE name LIKE '%' || :query || '%'")
    suspend fun getTotalCount(query: String): Int

    @Query(
        """
        SELECT * 
        FROM CryptoListingEntity 
        WHERE cryptoId IN (SELECT crypto FROM FavouriteEntity)
    """
    )
    fun getCryptoListingsInFavorites(): Flow<List<CryptoListingEntity>>


    @Query(
        """
        SELECT * 
        FROM CryptoListingEntity 
        WHERE cryptoId = :cryptoId
    """
    )
    suspend fun getCryptoListingById(cryptoId: String): CryptoListingEntity?

    // Запрос для получения всех имен в виде списка строк
    @Query("SELECT name FROM CryptoListingEntity")
    suspend fun getAllNames(): List<String>

    // Запрос для получения объекта по имени
    @Query("SELECT * FROM CryptoListingEntity WHERE name = :name LIMIT 1")
    suspend fun getCryptoByName(name: String): CryptoListingEntity?
}