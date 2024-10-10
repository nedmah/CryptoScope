package com.example.cryptolisting.domain.repository

interface FavouritesRepository {

    suspend fun addFavourite(cryptoId : String)

    suspend fun removeFavourite(cryptoId: String)

    suspend fun getFavourites() : List<String>

    suspend fun isFavourite(cryptoId: String) : Boolean
}