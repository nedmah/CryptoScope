package com.example.crypto_info.presentation.comparison

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.common_ui.composable.chart.CompareLineCharts
import com.example.common_ui.theme.paddings
import com.example.crypto_info.presentation.IntervalButtonGroup

@Composable
fun CryptoComparisonScreen(
    onBack: () -> Unit,
    onCompare: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = MaterialTheme.paddings.medium,
                end = MaterialTheme.paddings.medium,
                top = MaterialTheme.paddings.extraLarge
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ComparisonHeader(onBack = onBack, onCompare = onCompare, "", "")

        CompareLineCharts(lineChartData = listOf())

        IntervalButtonGroup(
            modifier = Modifier.padding(bottom = MaterialTheme.paddings.extraMedium),
            onIntervalChanged = {}
        )

        //TODO Сравнительные айтемы
    }
}