package com.example.wallet.data.local.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.db.daos.WalletChartDao
import com.example.core.data.db.entities.WalletChartEntity
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import com.example.core.util.formatPriceWithCurrency
import com.example.core.util.formatTimestamp
import com.example.wallet.domain.model.BalanceHistoryModel
import javax.inject.Inject

class WalletHistoryPagingSource (
    private val dao : WalletChartDao,
    private val dataStore: SettingsDataStore,
): PagingSource<Int,BalanceHistoryModel>() {

    override fun getRefreshKey(state: PagingState<Int, BalanceHistoryModel>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BalanceHistoryModel> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize

            val currencyCode = dataStore.getString(SettingsConstants.CURRENCY) ?: "USD"
            val currencyRate = dataStore.getDouble(SettingsConstants.CURRENCY_RATE) ?: 1.0

            val data = dao.getPagedData().load(params) as LoadResult.Page<Int, WalletChartEntity>
            val entries = data.data

            val items = entries.mapIndexed { index, entry ->
                val previousPrice = entries.getOrNull(index + 1)?.price ?: entry.price
                val profit = entry.price - previousPrice
                val percent = if (previousPrice != 0.0) (profit / previousPrice) * 100 else 0.0

                BalanceHistoryModel(
                    time = formatTimestamp(entry.timestamp),
                    price = formatPriceWithCurrency(entry.price, currencyCode, currencyRate),
                    profit = formatPriceWithCurrency(profit,currencyCode, currencyRate),
                    percent = "%.2f".format(percent)
                )
            }

            LoadResult.Page(
                data = items,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}