package com.example.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    suspend fun addFavourite(cryptoId : String)

    suspend fun removeFavourite(cryptoId: String)

    suspend fun getFavourites() : List<String>

    suspend fun getFavouritesFlow() : Flow<List<String>>

    suspend fun isFavourite(cryptoId: String) : Boolean
}