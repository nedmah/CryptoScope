package com.example.core.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.db.entities.FavouriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavouriteEntity)

    @Query("DELETE FROM favouriteentity WHERE crypto = :cryptoId")
    suspend fun deleteFavorite(cryptoId: String)

    @Query("SELECT crypto FROM favouriteentity")
    suspend fun getAllFavorites(): List<String>

    @Query("SELECT crypto FROM favouriteentity")
    fun getAllFavoritesFlow(): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM favouriteentity WHERE crypto = :cryptoId")
    suspend fun isFavourite(cryptoId : String) : Boolean
}