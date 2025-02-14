package com.example.wallet.data.local.data_source

import com.example.core.data.db.daos.CryptoListingsDao
import com.example.core.data.mappers.toCryptoListingsModel
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.model.CryptoListingsModel
import com.example.core.domain.settings.SettingsDataStore
import com.example.core.util.Resource
import com.example.wallet.domain.data_source.FavoriteCoinsDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteCoinsDatasourceImpl @Inject constructor(
    private val dao: CryptoListingsDao,
    private val dataStore: SettingsDataStore
) : FavoriteCoinsDatasource {

    override suspend fun getMyCoinsListings(): Flow<List<CryptoListingsModel>> {

        val currencyCode = dataStore.getString(SettingsConstants.CURRENCY) ?: "USD"
        val currencyRate = dataStore.getDouble(SettingsConstants.CURRENCY_RATE) ?: 1.0

        return dao.getCryptoListingsInFavorites().flowOn(Dispatchers.IO)
            .map { it.map { item -> item.toCryptoListingsModel(currencyCode, currencyRate) } }
    }

}