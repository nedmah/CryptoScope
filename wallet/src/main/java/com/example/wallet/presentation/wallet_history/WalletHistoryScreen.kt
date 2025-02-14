package com.example.wallet.presentation.wallet_history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common_ui.composable.BalanceHistoryItem
import com.example.common_ui.composable.PercentageTextCard
import com.example.common_ui.composable.chart.CustomLineChart
import com.example.common_ui.theme.extraColor
import com.example.common_ui.theme.model.Paddings
import com.example.common_ui.theme.paddings
import com.example.wallet.R

@Composable
fun WalletHistoryScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WalletHistoryViewModel = viewModel(factory = getViewModelFactory())
) {

    val state = viewModel.state.collectAsState().value
    val history = viewModel.balanceHistory.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onBack()
                }
                .padding(
                    top = MaterialTheme.paddings.large,
                    start = MaterialTheme.paddings.medium,
                    end = MaterialTheme.paddings.medium,
                )
                .align(Alignment.Start),
            painter = painterResource(id = com.example.common_ui.R.drawable.ic_back_24),
            contentDescription = null
        )

        Text(
            modifier = modifier.padding(
                top = MaterialTheme.paddings.small,
                bottom = MaterialTheme.paddings.large,
                start = MaterialTheme.paddings.medium,
                end = MaterialTheme.paddings.medium,
            ),
            text = stringResource(id = com.example.common_ui.R.string.balance),
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            modifier = modifier.padding(Paddings.extraMedium),
            text = state.balance,
            style = MaterialTheme.typography.headlineSmall
        )

        PercentageTextCard(percent = state.percentage)

        state.chartData?.let { data ->
            CustomLineChart(
                modifier = modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.paddings.small),
                lineChartData = data
            )
        }

        Text(
            modifier = modifier
                .align(Alignment.Start)
                .padding(
                    top = MaterialTheme.paddings.large,
                    bottom = MaterialTheme.paddings.small,
                    start = MaterialTheme.paddings.medium,
                    end = MaterialTheme.paddings.medium,
                ),
            text = stringResource(id = com.example.common_ui.R.string.balance_history),
            style = MaterialTheme.typography.bodyMedium
        )


        if (state.loading) CircularProgressIndicator()
        else {
            state.error?.let {
                Text(
                    modifier = modifier.padding(vertical = MaterialTheme.paddings.medium),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            LazyColumn(
                modifier = modifier.padding(horizontal = MaterialTheme.paddings.medium)
            ) {
                items(history.itemCount) { index ->
                    val item = history[index]
                    item?.let {
                        BalanceHistoryItem(
                            balance = item.price,
                            date = item.time,
                            profit = item.profit,
                            percent = item.percent
                        )
                        HorizontalDivider()
                    }
                }

                when (history.loadState.append) {
                    is LoadState.Error -> {}
                    LoadState.Loading -> item { CircularProgressIndicator(modifier = Modifier.fillMaxWidth()) }
                    is LoadState.NotLoading -> {}
                }
            }
        }

    }

}