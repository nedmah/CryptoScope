@file:OptIn(ExperimentalPagingApi::class)

package com.example.news.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.core.data.db.CryptoDb
import com.example.core.data.db.daos.CryptoNewsDao
import com.example.core.data.db.entities.CryptoNewsEntity
import com.example.news.data.toCryptoNewsEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class CryptoNewsRemoteMediator(
    private val db: CryptoDb,
    private val api: CryptoNewsApi
) : RemoteMediator<Int, CryptoNewsEntity>(){

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CryptoNewsEntity>
    ): MediatorResult {


        return try {
            val loadKey = when(loadType){
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null){
                        1
                    }else{
                        val key = (lastItem.id / state.config.pageSize) + 1
                        key % 100  // never be greater than 100
                    }

                }
            }

            delay(2000)
            val news = api.getCryptoNews(
                page = loadKey,
                count = state.config.pageSize
            )

            db.withTransaction {
                if(loadType == LoadType.REFRESH){
                    db.getCryptoNewsDao().clearAllData()
                }
                val newsEntities = news.result.map { it.toCryptoNewsEntity() }
                db.getCryptoNewsDao().upsertAll(newsEntities)
            }

            MediatorResult.Success(news.result.isEmpty())

        } catch (e : IOException){
            MediatorResult.Error(e)
        } catch (e : HttpException){
            MediatorResult.Error(e)
        }
    }

}