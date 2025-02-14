package com.example.currency.data.remote

import retrofit2.http.GET


interface CurrencyRatesApi {

    @GET("/currencies")
    suspend fun getCurrencyRates() : CurrencyRatesDto

}