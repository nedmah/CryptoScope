package com.example.cryptolisting.presentation

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common_ui.composable.CryptoSearchBar
import com.example.common_ui.theme.paddings
import com.example.cryptolisting.domain.model.CryptoListingsModel
import com.example.cryptolisting.presentation.composables.CryptoSwipeableItem
import com.example.cryptolisting.presentation.composables.FilterBottomSheet
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CryptoListingsScreen(
    modifier: Modifier = Modifier,
    viewModel: CryptoListingViewModel = hiltViewModel(),
    navigate: (Bundle) -> Unit
) {
    val state = viewModel.state
    val cryptosPagingItems = viewModel.pageFlow.collectAsLazyPagingItems()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth().padding(top = MaterialTheme.paddings.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CryptoSearchBar(
                value = state.searchQuery,
                onValueChange = { viewModel.onEvent(CryptoListingsEvents.OnSearchQueryChange(it)) }
            )

            IconButton(
                onClick = {
                    viewModel.onEvent(CryptoListingsEvents.OnFilterIconPushed)
                })
            {
                Icon(
                    modifier = modifier,
                    painter = painterResource(id = com.example.common_ui.R.drawable.ic_filter_24),
                    contentDescription = null
                )
            }

        }


        if (cryptosPagingItems.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = modifier.padding(top = MaterialTheme.paddings.large))
        } else {

            SwipeRefresh(
                modifier = modifier,
                state = swipeRefreshState,
                onRefresh = {
                    cryptosPagingItems.refresh()

                }
            ) {
                LazyColumn() {
                    if (state.cryptos.isNotEmpty() || state.searchQuery.isNotBlank()) {
                        items(state.cryptos.size) { index ->
                            val crypto = state.cryptos[index]
                            CryptoSwipeableItem(
                                cryptoModel = crypto,
                                onClick = { navigateWithBundle(crypto, navigate) },
                                onFavouriteAdd = {})
                        }
                    } else {
                        if (cryptosPagingItems.loadState.refresh != LoadState.Loading) {
                            items(cryptosPagingItems.itemCount) {
                                val crypto = cryptosPagingItems[it]
                                if (crypto != null) {
                                    CryptoSwipeableItem(
                                        cryptoModel = crypto,
                                        onClick = { navigateWithBundle(crypto, navigate) },
                                        onFavouriteAdd = {})
                                }
                            }
                        }
                    }

                    item {
                        if (cryptosPagingItems.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }

    if (state.isBottomDialogOpened){
        FilterBottomSheet(
            onFilterSelected = { viewModel.onEvent(CryptoListingsEvents.Filter(it)) },
            onDismiss = {viewModel.onEvent(CryptoListingsEvents.OnFilterDismiss)}
        )
    }

}

private fun navigateWithBundle(
    model: CryptoListingsModel,
    navigate: (Bundle) -> Unit
) {
    val bundle = Bundle().apply {
        putParcelable("cryptoInfo", model)
    }
    navigate(bundle)
}