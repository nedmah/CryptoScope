package com.example.crypto_info.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.common_ui.composable.FavoriteIcon
import com.example.common_ui.composable.chart.CustomLineChart
import com.example.common_ui.theme.model.Paddings
import com.example.common_ui.theme.paddings
import com.example.core.util.formatPriceString
import com.example.crypto_info.R
import com.example.crypto_info.domain.model.CryptoInfo
import com.example.cryptolisting.domain.model.CryptoListingsModel
import me.bytebeats.views.charts.line.LineChartData

@Composable
fun CryptoInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: CryptoInfoViewModel = hiltViewModel(),
    onIntervalChanged : (TimeIntervals) -> Unit,
    onBack: () -> Unit
) {

    val state = viewModel.state.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.paddings.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onBack()
                },
                painter = painterResource(id = com.example.common_ui.R.drawable.ic_back_24),
                contentDescription = null
            )

            Box(
                modifier = modifier.weight(1f),
                contentAlignment = Alignment.Center
            ){
                Text(
                    modifier = modifier,
                    text = state.name,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            FavoriteIcon() {}
        }

        AsyncImage(
            modifier = modifier
                .size(45.dp),
            model = state.icon,
            error = painterResource(id = com.example.common_ui.R.drawable.ic_error_24),
            contentDescription = null,
        )

        Text(
            modifier = modifier.padding(Paddings.extraMedium),
            text = formatPriceString(state.price),
            style = MaterialTheme.typography.headlineSmall
            )

        if (state.loading) CircularProgressIndicator()
        else{
            CustomLineChart(
                modifier = modifier.fillMaxHeight(0.4f),
                lineChartData = LineChartData(
                    state.cryptoInfo.prices.mapIndexed { index,price ->
                        LineChartData.Point(price,index.toString())
                    }
                )
            )
        }





//        Text(text = state.cryptoInfo.prices.toString())


        IntervalButtonGroup(
            onIntervalChanged = {
                viewModel.onEvent(CryptoInfoEvents.OnIntervalPushed(it))
            }
        )
    }
}