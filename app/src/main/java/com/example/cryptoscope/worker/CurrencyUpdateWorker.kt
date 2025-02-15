package com.example.cryptoscope.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import com.example.cryptoscope.di.CryptoScopeApplication
import com.example.currency.data.remote.CurrencyRatesApi
import javax.inject.Inject

class CurrencyUpdateWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context,workerParams) {

    @Inject lateinit var api: CurrencyRatesApi
    @Inject lateinit var dataStore: SettingsDataStore

    init {
        (context.applicationContext as CryptoScopeApplication).appComponent.inject(this)
    }

    override suspend fun doWork(): Result {

        val currency = dataStore.getString(SettingsConstants.CURRENCY) ?: "USD"

        if (currency == "USD" || currency.isEmpty()) {
            return Result.success() // Не обновляем, если USD или пусто
        }

        return try {
            val currencies = api.getCurrencyRates()
            val rate = currencies.result.getValue(currency.lowercase())
            dataStore.putDouble(SettingsConstants.CURRENCY_RATE, rate)
            Result.success()
        }catch (e: Exception) {
            Result.retry() // retry if fail
        }
    }


}