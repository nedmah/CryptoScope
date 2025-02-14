package com.example.wallet.presentation.my_coins


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common_ui.composable.MyCoinsItem
import com.example.common_ui.theme.paddings

@SuppressLint("DefaultLocale")
@Composable
fun MyCoinsScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyCoinsViewModel = viewModel(factory = getViewModelFactory())
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
            modifier = modifier.padding(top = MaterialTheme.paddings.xxLarge),
            text = stringResource(id = com.example.common_ui.R.string.my_coins),
            style = MaterialTheme.typography.headlineSmall
        )

        if (state.isLoading) CircularProgressIndicator()
        else if (state.error.isNotBlank()) Text(text = state.error)
        else
            LazyColumn {
                items(state.myCoins.size){ index ->
                    val coin = state.myCoins[index]
                    MyCoinsItem(
                        icon = coin.imgUrl,
                        symbol = coin.symbol,
                        name = coin.name,
                        price = coin.price,
                        percentage = coin.percentOneDay.toString(),
                        amount = coin.amount.toString(),
                        sum = coin.price
                            .filter { it.isDigit() || it == '.' }
                            .toDoubleOrNull()
                            ?.times(coin.amount)
                            ?.let { String.format("%.2f", it) } ?: "0.00"
                    ) {
                        //TODO навигация
                    }
                }
            }


    }

}
