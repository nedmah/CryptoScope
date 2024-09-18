@file:OptIn(ExperimentalPagingApi::class)

package com.example.cryptolisting.data.repository

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.core.util.Resource
import com.example.cryptolisting.data.local.CryptoDb
import com.example.cryptolisting.data.local.CryptoListingEntity
import com.example.cryptolisting.data.local.CryptoListingsDao
import com.example.cryptolisting.data.remote.CryptoListingsApi
import com.example.cryptolisting.data.toCryptoEntity
import com.example.cryptolisting.data.toCryptoListingsModel
import com.example.cryptolisting.domain.model.CryptoListingsModel
import com.example.cryptolisting.domain.repository.CryptoListingsRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CryptoListingRepositoryImpl @Inject constructor(
    private val api: CryptoListingsApi,
    private val db: CryptoDb
) : CryptoListingsRepository, RemoteMediator<Int, CryptoListingEntity>() {

    override suspend fun getListings(
        fetchFromNetwork: Boolean,
        query: String
    ): Flow<Resource<List<CryptoListingsModel>>> {
        return flow {
            emit(Resource.Loading())

            val localListings = db.getCryptoListingsDao().searchForListings(query)
            emit(Resource.Success(localListings.map { it.toCryptoListingsModel() }))

            val dbIsEmpty = query.isBlank() || localListings.isEmpty()
            val shouldLoadFromCache = !dbIsEmpty && !fetchFromNetwork
            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Success(
                data = db.getCryptoListingsDao().searchForListings("")
                    .map { it.toCryptoListingsModel() }
            ))
            emit(Resource.Loading(false))

        }
    }

    @SuppressLint("NewApi")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CryptoListingEntity>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }

                }
            }

            Log.d("TAG", "load: $loadKey")

            delay(2000)
            val cryptos = api.getCryptoListings(
                page = loadKey,
                pageCount = state.config.pageSize
            ).listings

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.getCryptoListingsDao().clearListings()
                }
                val cryptoEntities = cryptos.map { it.toCryptoEntity() }
                db.getCryptoListingsDao().saveCryptoListings(cryptoEntities)
            }

            MediatorResult.Success(cryptos.isEmpty())

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (@SuppressLint("NewApi") e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}