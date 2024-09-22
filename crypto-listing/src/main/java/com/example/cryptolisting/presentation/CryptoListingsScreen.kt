package com.example.cryptolisting.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common_ui.composable.CryptoScrollableLazyScaffold
import com.example.common_ui.composable.CryptoSearchBar
import com.example.common_ui.shimmerEffect
import com.example.common_ui.theme.model.Paddings
import com.example.cryptolisting.presentation.composables.CryptoItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CryptoListingsScreen(
    modifier: Modifier = Modifier,
    viewModel: CryptoListingViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val cryptos = viewModel.pageFlow.collectAsLazyPagingItems()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CryptoSearchBar(
            value = state.searchQuery,
            onValueChange = { viewModel.onEvent(CryptoListingsEvents.OnSearchQueryChange(it)) }
        )

        if (cryptos.loadState.refresh is LoadState.Loading && state.cryptos.isEmpty()) {
            CircularProgressIndicator()
        } else {

            SwipeRefresh(
                modifier = modifier,
                state = swipeRefreshState,
                onRefresh = {
                    cryptos.refresh()

                }
            ) {
                LazyColumn() {
                    if (state.searchQuery.isNotBlank()) {
                        items(state.cryptos.size) { index ->
                            val crypto = state.cryptos[index]
                            CryptoItem(cryptoModel = crypto)
                        }
                    } else {
                        if (cryptos.loadState.refresh != LoadState.Loading) {
                            items(cryptos.itemCount) {
                                val crypto = cryptos[it]
                                if (crypto != null) {
                                    CryptoItem(cryptoModel = crypto)
                                }
                            }
                        }
                    }

                    item {
                        if (cryptos.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}