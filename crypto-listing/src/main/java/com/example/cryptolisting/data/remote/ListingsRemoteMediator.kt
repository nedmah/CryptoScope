@file:OptIn(ExperimentalPagingApi::class)

package com.example.cryptolisting.data.remote

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cryptolisting.data.local.CryptoDb
import com.example.cryptolisting.data.local.CryptoListingEntity
import com.example.cryptolisting.data.toCryptoEntity
import kotlinx.coroutines.delay
import java.io.IOException
import javax.inject.Inject

class ListingsRemoteMediator @Inject constructor(
    private val api: CryptoListingsApi,
    private val db: CryptoDb
) : RemoteMediator<Int, CryptoListingEntity>() {

    @SuppressLint("NewApi")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CryptoListingEntity>,
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