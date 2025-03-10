@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cryptolisting.presentation

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common_ui.composable.CryptoItemPlaceholder
import com.example.common_ui.composable.CryptoSearchBar
import com.example.common_ui.composable.CryptoSwipeableItem
import com.example.common_ui.theme.paddings
import com.example.core.util.extensions.navigateCryptoListingModelWithBundle
import com.example.cryptolisting.presentation.composables.FilterBottomSheet
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun CryptoListingsScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navigate: (Bundle) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CryptoListingViewModel = viewModel(factory = getViewModelFactory())
) {

    val state = viewModel.state.collectAsState().value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val items = state.listings.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.paddings.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CryptoSearchBar(
                modifier = modifier.weight(1f),
                value = state.searchQuery,
                onValueChange = {
                    viewModel.onEvent(CryptoListingsEvents.OnSearchQueryChange(it))
                }
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

        if (state.isLoading) {
            LazyColumn {
                items(8){
                    CryptoItemPlaceholder()
                }
            }
        } else {
            SwipeRefresh(
                modifier = modifier,
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.onEvent(CryptoListingsEvents.Refresh)
                }
            ) {
                if (state.error.isNotBlank())
                    Text(text = state.error)
                else
                    LazyColumn {
                        items(items.itemCount, key = { index -> items[index]?.cryptoId ?: index }) { index ->
                            val crypto = items[index]
                            if (crypto == null) {
                                CryptoItemPlaceholder()
                            } else {
                                CryptoSwipeableItem(
                                    cryptoModel = crypto,
                                    isFavourite = crypto.isFavorite,
                                    onFavouriteAdd = {
                                        viewModel.onEvent(
                                            CryptoListingsEvents.OnFavourite(
                                                crypto.cryptoId
                                            )
                                        )
                                    }) {
                                    navigateCryptoListingModelWithBundle(crypto, navigate)
                                }
                            }
                        }

                    }
            }
        }
    }

    if (state.isBottomDialogOpened)
        FilterBottomSheet(
            onFilterSelected = {
                viewModel.onEvent(CryptoListingsEvents.Filter(it))
            },
            onDismiss = {
                coroutineScope.launch {
                    sheetState.hide()
                    viewModel.onEvent(CryptoListingsEvents.OnFilterDismiss)
                }

            },
            sheetState = sheetState
        )

}
