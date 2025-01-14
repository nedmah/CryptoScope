package com.example.crypto_info.presentation.comparison

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common_ui.composable.chart.CompareLineCharts
import com.example.common_ui.theme.paddings
import com.example.common_ui.theme.spacers
import com.example.core.util.formatPriceString
import com.example.crypto_info.presentation.IntervalButtonGroup
import com.example.crypto_info.presentation.composables.CryptoComparisonItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.bytebeats.views.charts.line.LineChartData

@Composable
fun CryptoComparisonScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CryptoComparisonViewModel = viewModel(factory = getViewModelFactory())
) {

    val state = viewModel.state.collectAsState().value
    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedOption1 by remember { mutableStateOf("") }
    var selectedOption2 by remember { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.paddings.extraLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        ComparisonHeader(
            modifier = modifier.padding(horizontal = MaterialTheme.paddings.medium),
            onBack = onBack,
            onCompare = {
                viewModel.onEvent(ComparisonEvents.OnCompareClicked)
                isDialogVisible = true
            },
            iconOne = state.image1Url,
            iconTwo = state.image2Url
        )

        if (state.loading) CircularProgressIndicator()
        else if(!state.errorChart.isNullOrBlank()) Text(text = state.errorChart)
        else {
            CompareLineCharts(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth(),
                lineChartData = listOf(state.cryptoChart1, state.cryptoChart2)
            )
        }

        IntervalButtonGroup(
            modifier = Modifier.padding(
                bottom = MaterialTheme.paddings.small
            ),
            onIntervalChanged = {viewModel.onEvent(ComparisonEvents.OnIntervalPushed(it))}
        )

        LazyColumn(modifier = modifier.padding()) {
            itemsIndexed(state.cryptoData) { index, (title, values) ->

                val backgroundColor =
                    if (index % 2 == 0) MaterialTheme.colorScheme.background
                    else MaterialTheme.colorScheme.surface

                CryptoComparisonItem(
                    title = title,
                    text1 = values.first,
                    text2 = values.second,
                    formatPrice = { formatPriceString(it) },
                    background = backgroundColor
                )
            }
            item{
                Spacer(modifier = modifier.height(96.dp))
            }
        }

    }

    if (isDialogVisible) {
        CompareDialog(
            options1 = state.cryptoNames,
            options2 = state.cryptoNames,
            onDismissRequest = { isDialogVisible = false },
            onConfirm = {
                isDialogVisible = false
                viewModel.onEvent(
                    ComparisonEvents.OnCompareConfirmed(
                        selectedOption1,
                        selectedOption2
                    )
                )
            },
            selectedOption1 = selectedOption1,
            onOption1Selected = { selectedOption1 = it },
            selectedOption2 = selectedOption2,
            onOption2Selected = { selectedOption2 = it }
        )
    }
}