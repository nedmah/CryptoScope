package com.example.currency.data.remote

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.core.util.Resource
import com.example.core.util.mapExceptionToMessage
import com.example.currency.data.toCurrencyList
import com.example.currency.domain.CurrencyDataSource
import com.example.currency.domain.CurrencyModel
import retrofit2.HttpException
import javax.inject.Inject

class CurrencyDataSourceImpl @Inject constructor(
    private val api: CurrencyRatesApi
) : CurrencyDataSource {


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun fetchCurrencies(): Resource<List<CurrencyModel>> {
        return try {
            val result = api.getCurrencyRates().result
            Resource.Success(data = result.toCurrencyList())
        } catch (e : Exception){
            Resource.Error(message = mapExceptionToMessage(e))
        }
    }

}