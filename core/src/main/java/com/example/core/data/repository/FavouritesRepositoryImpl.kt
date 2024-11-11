package com.example.core.data.repository

import com.example.core.data.db.daos.FavouritesDao
import com.example.core.data.db.entities.FavouriteEntity
import com.example.core.domain.repository.FavouritesRepository
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val dao : FavouritesDao
) : FavouritesRepository {

    override suspend fun addFavourite(cryptoId: String) {
        dao.insertFavorite(FavouriteEntity(crypto = cryptoId))
    }

    override suspend fun removeFavourite(cryptoId: String) {
        dao.deleteFavorite(cryptoId)
    }

    override suspend fun getFavourites(): List<String> {
        return dao.getAllFavorites()
    }

    override suspend fun isFavourite(cryptoId: String): Boolean {
        return dao.isFavourite(cryptoId)
    }
}