package com.example.news.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common_ui.composable.CryptoSearchBar
import com.example.common_ui.theme.paddings
import com.example.core.util.openCustomTab
import com.example.news.R
import com.example.news.domain.model.CryptoNewsModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CryptoNewsScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(factory = getViewModelFactory())
) {

    val context = LocalContext.current
    val news = viewModel.pageFlow.collectAsLazyPagingItems()

    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = news.loadState.refresh is LoadState.Loading)

    LaunchedEffect(key1 = news.loadState) {
        if (news.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                (news.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = modifier.padding(
                top = MaterialTheme.paddings.extraLarge,
                bottom = MaterialTheme.paddings.extraLarge
            ),
            text = stringResource(id = com.example.common_ui.R.string.news),
            style = MaterialTheme.typography.headlineSmall
        )


        SwipeRefresh(
            modifier = modifier,
            state = swipeRefreshState,
            onRefresh = {
                news.refresh()
            }
        ) {
            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (news.loadState.refresh is LoadState.Loading)
                    items(8) { CryptoNewsItemPlaceholder() }
                else {
                    items(news.itemCount) {
                        val model = news[it]
                        if (model != null) {
                            CryptoNewsItem(
                                title = model.title,
                                source = model.source,
                                date = model.date,
                                imageUrl = model.imgUrl
                            ) {
                                openCustomTab(context, model.link)
                            }
                        }
                    }

                    if (news.loadState.append is LoadState.Loading) {
                        items(3) {
                            CryptoNewsItemPlaceholder()
                        }
                    }
                }
            }

        }
    }
}