package com.example.wallet.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common_ui.composable.CryptoItem
import com.example.common_ui.composable.CryptoItemSmall
import com.example.common_ui.composable.CryptoItemWallet
import com.example.common_ui.composable.CryptoSwipeableItem
import com.example.common_ui.composable.PreviewWrapper
import com.example.common_ui.composable.WalletCard
import com.example.common_ui.theme.extraColor
import com.example.common_ui.theme.model.Paddings
import com.example.common_ui.theme.paddings
import com.example.core.util.formatPriceString
import com.example.wallet.R
import com.example.wallet.domain.model.CryptoWalletModel

@Composable
fun CryptoWalletScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    modifier: Modifier = Modifier,
    viewModel: CryptoWalletViewModel = viewModel(factory = getViewModelFactory()),
) {

    val state = viewModel.state.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = modifier.padding(top = MaterialTheme.paddings.xxLarge, bottom = MaterialTheme.paddings.xxLarge),
            text = "Кошелёк",
            style = MaterialTheme.typography.headlineSmall
        )

        WalletCard(
            balance = formatPriceString(state.wallet.balance),
            profit = formatPriceString(state.wallet.profitOrAddition),
            percentage = state.wallet.percentage
        ) {
            //TODO: навигация
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.paddings.extraLarge),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Мои монеты", style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = modifier.clickable {
                //TODO кнопка открыть
            },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier.padding(horizontal = MaterialTheme.paddings.small),
                    text = "Открыть",
                    color = MaterialTheme.extraColor.chart,
                    style = MaterialTheme.typography.bodyMedium
                )
                Icon(
                    painter = painterResource(id = com.example.common_ui.R.drawable.arrow_right),
                    tint = MaterialTheme.extraColor.chart,
                    contentDescription = null
                )
            }
        }

        if (state.myCoinsLoading) {
            CircularProgressIndicator()
        } else {
            state.myCoinsError?.let {
                Text(
                    modifier = modifier.padding(vertical = MaterialTheme.paddings.medium),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            LazyRow {
                items(state.myCoins.size) { index ->
                    val crypto = state.myCoins[index]
                    CryptoItemSmall(cryptoModel = crypto) {
                        //TODO навигация
                    }
                }
            }
        }


        Text(
            modifier = modifier
                .align(Alignment.Start)
                .padding(top = MaterialTheme.paddings.large),
            text = "Избранные монеты",
            style = MaterialTheme.typography.bodyMedium
        )

        if (state.myCoinsLoading) {
            CircularProgressIndicator()
        } else {
            state.favouriteCoinsError?.let {
                Text(
                    modifier = modifier.padding(vertical = MaterialTheme.paddings.medium),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            LazyColumn {
                items(state.favouriteCoins.size) { index ->
                    val crypto = state.favouriteCoins[index]
                    CryptoItem(
                        cryptoModel = crypto,
                        onClick = {
                            //TODO навигация
                        }
                    )
                }
            }
        }

    }
}

@Composable
@PreviewLightDark
fun PreviewWallet(

) {
//    PreviewWrapper {
//        CryptoWalletScreen(
//            walletModel = CryptoWalletModel("2,549.370",false, "75.982", "0.12")
//        )
//    }
}