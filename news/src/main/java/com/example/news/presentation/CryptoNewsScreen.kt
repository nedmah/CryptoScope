package com.example.news.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.domain.model.CryptoNewsModel

@Composable
fun CryptoNewsScreen(
    modifier: Modifier = Modifier,
    getViewModelFactory: () -> ViewModelProvider.Factory,
    viewModel: NewsViewModel = viewModel(factory = getViewModelFactory())
) {

    val context = LocalContext.current
    val news = viewModel.pageFlow.collectAsLazyPagingItems()

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

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (news.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(news.itemCount) {
                    val model = news[it]
                    if (model != null) {
                        CryptoNewsItem(
                            title = model.title,
                            source = model.source,
                            date = model.date,
                            imageUrl = model.imgUrl
                        )
                    }
                }

                item {
                    if (news.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                        )
                    }
                }

            }
        }
    }

}