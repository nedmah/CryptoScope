package com.example.cryptolisting.data.remote

import com.example.core.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoListingsApi {

    @GET("/coins")
    suspend fun getCryptoListings(
        @Query("limit") count : Int
    ) : CryptoListingDto



}