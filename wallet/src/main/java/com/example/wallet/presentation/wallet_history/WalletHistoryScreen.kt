package com.example.wallet.presentation.wallet_history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common_ui.composable.BalanceHistoryItem
import com.example.common_ui.composable.PercentageTextCard
import com.example.common_ui.theme.extraColor
import com.example.common_ui.theme.model.Paddings
import com.example.common_ui.theme.paddings
import com.example.core.util.formatPriceString
import com.example.wallet.presentation.CryptoWalletViewModel
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.GradientLineShader
import me.bytebeats.views.charts.line.render.point.EmptyPointDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Composable
fun WalletHistoryScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    onBack : () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WalletHistoryViewModel = viewModel(factory = getViewModelFactory())
) {

    val state = viewModel.state.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
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
                .padding(top = MaterialTheme.paddings.xxLarge)
                .align(Alignment.Start)
            ,
            painter = painterResource(id = com.example.common_ui.R.drawable.ic_back_24),
            contentDescription = null
        )

        Text(
            modifier = modifier.padding(
                top = MaterialTheme.paddings.small,
                bottom = MaterialTheme.paddings.xxLarge
            ),
            text = "Баланс",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            modifier = modifier.padding(Paddings.extraMedium),
            text = formatPriceString(state.balance),
            style = MaterialTheme.typography.headlineSmall
        )

        PercentageTextCard(percent = state.percentage)

        state.chartData?.let { data ->
            LineChart(
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp),
                animation = simpleChartAnimation(),
                pointDrawer = EmptyPointDrawer,
                lineShader = GradientLineShader(
                    colors = listOf(
                        MaterialTheme.extraColor.chartGradient,
                        Color.Transparent
                    )
                ),
                lineChartData = data
            )
        }

        Text(
            modifier = modifier
                .align(Alignment.Start)
                .padding(top = MaterialTheme.paddings.large),
            text = "История баланса",
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
            LazyColumn {
                items(state.historyData.size) { index ->
                    val historyItem = state.historyData[index]
                    BalanceHistoryItem(
                        balance = historyItem.totalBalance,
                        date = historyItem.date,
                        profit = historyItem.profitOrAddition,
                        percentOrName = historyItem.percentageChange,
                        isPercent = !historyItem.isAddition
                    )
                }
            }
        }

    }

}