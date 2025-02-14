
package com.example.cryptolisting.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.core.util.Resource
import com.example.core.data.db.daos.CryptoListingsDao
import com.example.core.data.mappers.toCryptoListingsEntity
import com.example.core.data.mappers.toCryptoListingsModel
import com.example.core.data.settings.SettingsConstants
import com.example.cryptolisting.data.remote.CryptoListingsApi
import com.example.core.domain.model.CryptoListingsModel
import com.example.core.domain.settings.SettingsDataStore
import com.example.cryptolisting.domain.repository.CryptoListingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CryptoListingRepositoryImpl @Inject constructor(
    private val dao: CryptoListingsDao,
    private val api: CryptoListingsApi,
    private val dataStore: SettingsDataStore
) : CryptoListingsRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getListings(
        fetchFromNetwork: Boolean,
        query: String
    ): Flow<Resource<List<CryptoListingsModel>>> {
        return flow {
            emit(Resource.Loading())

            val currencyCode = dataStore.getString(SettingsConstants.CURRENCY) ?: "USD"
            val currencyRate = dataStore.getDouble(SettingsConstants.CURRENCY_RATE) ?: 1.0

            val localListings = dao.searchForListings(query)
            emit(Resource.Success(localListings.map { it.toCryptoListingsModel(currencyCode, currencyRate) }))

            val dbIsEmpty = query.isBlank() || localListings.isEmpty()
            val shouldLoadFromCache = !dbIsEmpty && !fetchFromNetwork
            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                api.getCryptoListings(100)
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't retrieve data"))
                null
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't retrieve data"))
                null
            }


            remoteListings?.let {
                val listings = it.listings.map { item -> item.toCryptoListingsModel() }
                dao.clearListings()
                dao.saveCryptoListings(listings.map { item -> item.toCryptoListingsEntity() })
                emit(
                    Resource.Success(
                        data = dao
                            .searchForListings("")
                            .map { listingItem -> listingItem.toCryptoListingsModel(currencyCode, currencyRate) })
                )
                emit(Resource.Loading(false))
            }

        }
    }
}