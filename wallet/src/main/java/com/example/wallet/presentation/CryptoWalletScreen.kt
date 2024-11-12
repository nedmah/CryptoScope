package com.example.wallet.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common_ui.composable.CryptoItemWallet
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
    modifier: Modifier = Modifier,
    walletModel: CryptoWalletModel
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = modifier.padding(top = MaterialTheme.paddings.xxLarge),
            text = "Кошелёк",
            style = MaterialTheme.typography.headlineSmall
        )

        WalletCard(
            modifier = modifier.padding(top = MaterialTheme.paddings.medium),
            balance = walletModel.balance,
            profit = walletModel.profitOrAddition,
            percentage = walletModel.percentage
        ) {

        }

        Row(
            modifier = modifier.fillMaxWidth().padding(top = MaterialTheme.paddings.large),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Мои монеты",style = MaterialTheme.typography.bodyMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
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


    }
}

@Composable
@PreviewLightDark
fun PreviewWallet(

) {
    PreviewWrapper {
        CryptoWalletScreen(
            walletModel = CryptoWalletModel("2,549.370",false, "75.982", "0.12")
        )
    }
}