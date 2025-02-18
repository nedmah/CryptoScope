
package com.example.cryptolisting.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.util.Resource
import com.example.core.data.db.daos.CryptoListingsDao
import com.example.core.data.db.entities.CryptoListingEntity
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
    override suspend fun saveListings(
        fetchFromNetwork: Boolean
    ): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading(true))

            val localListings = dao.getAllListings()

            val shouldLoadFromCache = localListings.isNotEmpty() && !fetchFromNetwork
            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                emit(Resource.Success(Unit))
                return@flow
            }

            val remoteListings = try {
                api.getCryptoListings(300)
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: "Couldn't retrieve data"))
                return@flow
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: "Couldn't retrieve data"))
                return@flow
            }

            remoteListings?.let {
                val listings = it.listings.map { item -> item.toCryptoListingsModel() }
                dao.clearListings()
                dao.saveCryptoListings(listings.map { item -> item.toCryptoListingsEntity() })
                emit(Resource.Loading(false))
                emit(Resource.Success(Unit))
            }
        }
    }

    override fun getPagedListings(
        query: String,
        sortOrder: String
    ): Flow<PagingData<CryptoListingEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = true),
            pagingSourceFactory = {
                when (sortOrder) {
                    "id ASC" -> dao.getPagedDataByIdAsc(query)
                    "id DESC" -> dao.getPagedDataByIdDesc(query)
                    "price ASC" -> dao.getPagedDataByPriceAsc(query)
                    "price DESC" -> dao.getPagedDataByPriceDesc(query)
                    "percentage ASC" -> dao.getPagedDataByPercentageAsc(query)
                    "percentage DESC" -> dao.getPagedDataByPercentageDesc(query)
                    "name ASC" -> dao.getPagedDataByNameAsc(query)
                    "name DESC" -> dao.getPagedDataByNameDesc(query)
                    else -> dao.getPagedDataByIdAsc(query)
                }
            }
        ).flow
    }


}