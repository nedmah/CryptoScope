package com.example.currency.data.remote

import com.example.core.util.Resource
import com.example.currency.data.toCurrencyList
import com.example.currency.domain.CurrencyDataSource
import com.example.currency.domain.CurrencyModel
import retrofit2.HttpException
import javax.inject.Inject

class CurrencyDataSourceImpl @Inject constructor(
    private val api: CurrencyRatesApi
) : CurrencyDataSource {


    override suspend fun fetchCurrencies(): Resource<List<CurrencyModel>> {
        return try {
            val result = api.getCurrencyRates().result
            Resource.Success(data = result.toCurrencyList())
        } catch (e : HttpException){
            Resource.Error(message = e.message ?: "$e code: ${e.code()}")
        }
    }

}