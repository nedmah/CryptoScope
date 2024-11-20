package com.example.news.data.remote

import com.example.core.data.network.Constants.BASE_URL
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoNewsApi {

    @GET("/news")
    suspend fun getCryptoNews(
        @Query("page") page : Int,
        @Query("limit") count : Int
    ) : CryptoNewsDto
}