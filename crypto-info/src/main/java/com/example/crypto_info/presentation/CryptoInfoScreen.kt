package com.example.crypto_info.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.common_ui.composable.FavoriteIcon
import com.example.common_ui.composable.PercentageTextCard
import com.example.common_ui.composable.chart.CustomLineChart
import com.example.common_ui.theme.model.Paddings
import com.example.common_ui.theme.paddings
import com.example.core.util.formatPriceString
import com.example.crypto_info.R
import com.example.crypto_info.presentation.composables.CryptoInfoHeader
import com.example.crypto_info.presentation.composables.CryptoInfoItem
import com.example.crypto_info.presentation.composables.ExternalUrl
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.bytebeats.views.charts.line.LineChartData

@Composable
fun CryptoInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: CryptoInfoViewModel = hiltViewModel(),
    onBack: () -> Unit
) {

    val state = viewModel.state.collectAsState().value
    var chartDataState by remember { mutableStateOf(LineChartData(emptyList())) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(state.cryptoInfo) {
        coroutineScope.launch(Dispatchers.Default) {
            val mappedData = state.cryptoInfo.prices.mapIndexed { index, price ->
                LineChartData.Point(price, index.toString())
            }
            chartDataState = LineChartData(mappedData)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.paddings.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CryptoInfoHeader(
            name = state.name,
            isFavourite = state.isFavourite,
            onFavouritePushed = { viewModel.onEvent(CryptoInfoEvents.OnFavourite(state.cryptoId)) },
            onBack = onBack
        )

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

        PercentageTextCard(percent = state.percentage)





        if (state.loading || chartDataState.points.isEmpty()) CircularProgressIndicator()
        else {
            CustomLineChart(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.paddings.small, start = 0.dp, end = 0.dp),
                lineChartData = chartDataState
            )
        }

        IntervalButtonGroup(
            modifier = Modifier.padding(bottom = MaterialTheme.paddings.extraMedium),
            onIntervalChanged = {
                viewModel.onEvent(CryptoInfoEvents.OnIntervalPushed(it))
            }
        )

        Column(
            modifier = modifier.padding(horizontal = MaterialTheme.paddings.extraMedium)
        ) {
            CryptoInfoItem(text = "Market cap", value = state.marketCap)
            CryptoInfoItem(text = "Total supply", value = state.totalSupply)
            ExternalUrl(url = state.twitterUrl, imageId = com.example.common_ui.R.drawable.x_logo){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(state.twitterUrl))
                context.startActivity(intent)
            }
            ExternalUrl(url = state.redditUrl, imageId = com.example.common_ui.R.drawable.reddit_logo){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(state.redditUrl))
                context.startActivity(intent)
            }
        }

    }
}


