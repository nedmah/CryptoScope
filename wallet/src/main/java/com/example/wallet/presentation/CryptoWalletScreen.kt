package com.example.wallet.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.common_ui.BitcoinLoadingIndicator
import com.example.common_ui.composable.CryptoItem
import com.example.common_ui.composable.MyCoinsItemSmall
import com.example.common_ui.composable.WalletCard
import com.example.common_ui.theme.extraColor
import com.example.common_ui.theme.paddings
import com.example.common_ui.theme.spacers
import com.example.core.util.extensions.navigateCryptoListingModelWithBundle
import com.example.core.util.truncateMiddleText
import com.example.wallet.R
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CryptoWalletScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navigateWithBundle: (Bundle) -> Unit,
    navigateToMyCoins: () -> Unit,
    navigateToWalletHistory: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CryptoWalletViewModel = viewModel(factory = getViewModelFactory()),
) {

    val state = viewModel.state.collectAsState().value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.myCoinsLoading)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SwipeRefresh(
            modifier = modifier,
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(WalletEvents.onRefresh)
            }
        ) {

            WalletHeader(
                modifier = modifier,
                address = state.currentAddress,
                blockchain = state.currentBlockchain,
                imgUrl = state.currentBlockchainImage
            )

            Text(
                modifier = modifier.padding(
                    top = MaterialTheme.paddings.large,
                    bottom = MaterialTheme.paddings.medium
                ),
                text = stringResource(id = com.example.common_ui.R.string.wallet),
                style = MaterialTheme.typography.headlineSmall
            )

            WalletCard(
                balance = state.wallet.balance,
                profit = state.wallet.profit,
                percentage = state.wallet.percentage
            ) {
                navigateToWalletHistory()
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.paddings.extraLarge),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = com.example.common_ui.R.string.my_coins),
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    modifier = modifier.clickable {
                        navigateToMyCoins()
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = modifier.padding(horizontal = MaterialTheme.paddings.small),
                        text = stringResource(id = com.example.common_ui.R.string.open),
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
                BitcoinLoadingIndicator()
            } else {
                state.myCoinsError?.let {
                    Text(
                        modifier = modifier.padding(vertical = MaterialTheme.paddings.medium),
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } ?: LazyRow {
                    items(state.myCoins.size) { index ->
                        val crypto = state.myCoins[index]
                        MyCoinsItemSmall(
                            modifier = modifier.padding(horizontal = MaterialTheme.paddings.extraSmall),
                            imgUrl = crypto.imgUrl,
                            symbol = crypto.symbol,
                            name = crypto.name,
                            price = crypto.price,
                            percentOneDay = crypto.percentOneDay.toString()
                        ) {

                        }
                    }
                }
            }
        }

        Text(
            modifier = modifier
                .align(Alignment.Start)
                .padding(top = MaterialTheme.paddings.large),
            text = stringResource(id = com.example.common_ui.R.string.favorite_coins),
            style = MaterialTheme.typography.bodyMedium
        )

        state.favouriteCoinsError?.let {
            Text(
                modifier = modifier.padding(vertical = MaterialTheme.paddings.medium),
                text = it,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (state.favouriteCoins.isNotEmpty()) {
            LazyColumn {
                items(state.favouriteCoins.size) { index ->
                    val crypto = state.favouriteCoins[index]
                    CryptoItem(
                        cryptoModel = crypto,
                        onClick = {
                            navigateCryptoListingModelWithBundle(crypto, navigateWithBundle)
                        }
                    )
                }
            }
            Spacer(modifier = modifier.height(MaterialTheme.spacers.xxLarge))
        } else
            Text(
                modifier = modifier.padding(vertical = MaterialTheme.paddings.medium),
                text = stringResource(id = com.example.common_ui.R.string.coins_missing),
                style = MaterialTheme.typography.bodyMedium
            )

    }
}

@Composable
private fun WalletHeader(
    modifier: Modifier = Modifier,
    address: String?,
    blockchain: String?,
    imgUrl: String?
) {

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.paddings.xxLarge),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        address?.let {
            Row(
                modifier = modifier.clickable {
                    clipboardManager.setText(AnnotatedString(it))
                    Toast.makeText(
                        context,
                        context.getString(com.example.common_ui.R.string.copied),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = truncateMiddleText(it, 35),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraSmall))
                Icon(
                    modifier = modifier.size(12.dp, 14.dp),
                    painter = painterResource(id = com.example.common_ui.R.drawable.ic_copy_24),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                )
            }
        }

        blockchain?.let {
            Box(
                modifier = modifier.border(
                    1.dp,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    RoundedCornerShape(15.dp)
                )
            ) {
                Row(
                    modifier = modifier.padding(MaterialTheme.paddings.extraSmall)
                ) {
                    AsyncImage(
                        modifier = modifier
                            .size(20.dp)
                            .clip(CircleShape),
                        model = imgUrl,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraSmall))
                    Text(text = blockchain)
                }
            }
        }
    }
}