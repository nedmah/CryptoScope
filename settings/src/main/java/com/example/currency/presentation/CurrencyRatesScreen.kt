package com.example.currency.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common_ui.theme.extraColor
import com.example.common_ui.theme.paddings
import com.example.settings.R

@Composable
fun CurrencyRatesScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CurrencyRatesViewModel = viewModel(factory = getViewModelFactory())
) {

    val state = viewModel.state.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = modifier
                .align(Alignment.Start)
                .padding(top = MaterialTheme.paddings.extraLarge)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onBack()
                },
            painter = painterResource(id = com.example.common_ui.R.drawable.ic_back_24),
            contentDescription = null
        )

        Text(
            modifier = modifier.padding(
                top = MaterialTheme.paddings.xxLarge,
                bottom = MaterialTheme.paddings.xxLarge
            ),
            text = stringResource(id = com.example.common_ui.R.string.currency),
            style = MaterialTheme.typography.headlineSmall
        )

        if (state.error != null)
            Text(text = state.error)
        else
            LazyColumn {
                items(state.currencies){ currency ->
                    ListItem(
                        modifier = modifier.clickable {
                            viewModel.selectItem(currency.code,currency.currency)
                        },
                        headlineContent = {
                            Text(
                            text = "%.2f".format(currency.currency)
                                )
                        },
                        leadingContent = {
                            Text(
                                text = currency.code.uppercase(),
                                color = MaterialTheme.colorScheme.primary)
                        },
                        trailingContent = {
                            AnimatedVisibility(visible = state.selectedCurrency == currency.code) {
                                Icon(
                                    painter = painterResource(id = com.example.common_ui.R.drawable.ic_select_16),
                                    tint = MaterialTheme.extraColor.chart,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }
    }
}