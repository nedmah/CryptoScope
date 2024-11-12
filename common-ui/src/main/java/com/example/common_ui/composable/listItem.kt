package com.example.common_ui.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.common_ui.R
import com.example.common_ui.theme.paddings
import com.example.common_ui.theme.spacers
import com.example.core.domain.model.CryptoListingsModel
import com.example.core.util.formatPriceString
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun CryptoSwipeableItem(
    modifier: Modifier = Modifier,
    cryptoModel: CryptoListingsModel,
    isFavourite: Boolean,
    onFavouriteAdd: () -> Unit,
    onClick: () -> Unit
) {

    var contextMenuWidth by remember { mutableFloatStateOf(0f) }
    val offset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(30))
    ) {
        Row(
            modifier = Modifier
                .onSizeChanged {
                    contextMenuWidth = it.width.toFloat()
                }
                .align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FavoriteIcon(
                modifier = modifier.padding(start = MaterialTheme.paddings.medium),
                isFavourite = isFavourite
            ) {
                onFavouriteAdd()
                scope.launch {
                    offset.animateTo(0f)
                }
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(contextMenuWidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount)
                                    .coerceIn(-contextMenuWidth, 0f)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value <= -contextMenuWidth / 2f -> {
                                    scope.launch {
                                        offset.animateTo(-contextMenuWidth)
                                    }
                                }

                                else -> {
                                    scope.launch {
                                        offset.animateTo(0f)
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            CryptoItem(
                cryptoModel = cryptoModel,
                onClick = onClick
            )
        }
    }
}

@Composable
fun CryptoItem(
    modifier: Modifier = Modifier,
    cryptoModel: CryptoListingsModel,
    onClick: () -> Unit
) {

    androidx.compose.material3.ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.paddings.extraMedium)
            .clickable { onClick() },
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.paddings.extraMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                AsyncImage(
                    modifier = modifier.size(35.dp),
                    model = cryptoModel.icon,
                    error = painterResource(id = com.example.common_ui.R.drawable.ic_error_24),
                    contentDescription = null,
                )

                CryptoNameSymbol(symbol = cryptoModel.symbol, name = cryptoModel.name)
            }

            Column(
                modifier = modifier.padding(vertical = MaterialTheme.paddings.medium),
                horizontalAlignment = Alignment.End
            ) {
                PercentageText(
                    percent = cryptoModel.percentage
                )
                Text(
                    text = formatPriceString(cryptoModel.price),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
fun CryptoItemSmall(
    modifier: Modifier = Modifier,
    cryptoModel: CryptoListingsModel,
    onClick: () -> Unit
) {

    androidx.compose.material3.ElevatedCard(
        modifier = modifier
            .width(184.dp)
            .padding(vertical = MaterialTheme.paddings.extraMedium)
            .clickable { onClick() },
    ) {

        Column(
            modifier = modifier.padding(
                start = MaterialTheme.paddings.extraSmall,
                end = MaterialTheme.paddings.small,
                top = MaterialTheme.paddings.small,
                bottom = MaterialTheme.paddings.extraSmall
            )
        ) {
            Row {
                AsyncImage(
                    modifier = modifier
                        .size(40.dp),
                    model = cryptoModel.icon,
                    error = painterResource(id = com.example.common_ui.R.drawable.ic_error_24),
                    contentDescription = null,
                )
                CryptoNameSymbol(
                    symbol = cryptoModel.symbol,
                    name = cryptoModel.name,
                    padding = MaterialTheme.paddings.extraSmall
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.paddings.extraMedium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatPriceString(cryptoModel.price),
                    style = MaterialTheme.typography.titleSmall
                )

                PercentageText(
                    percent = cryptoModel.percentage
                )
            }
        }
    }
}


@Composable
fun CryptoSwipeableItemWallet(
    modifier: Modifier = Modifier,
    cryptoModel: CryptoListingsModel,
    amount : String,
    sum : String,
    onAction: () -> Unit,
    onClick: () -> Unit
) {

    var contextMenuWidth by remember { mutableFloatStateOf(0f) }
    val offset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(30))
    ) {
        Row(
            modifier = Modifier
                .onSizeChanged {
                    contextMenuWidth = it.width.toFloat()
                }
                .align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = modifier.background(color = MaterialTheme.colorScheme.error),
                onClick = onAction
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_delete_24), contentDescription = null)
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(contextMenuWidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount)
                                    .coerceIn(-contextMenuWidth, 0f)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value <= -contextMenuWidth / 2f -> {
                                    scope.launch {
                                        offset.animateTo(-contextMenuWidth)
                                    }
                                }

                                else -> {
                                    scope.launch {
                                        offset.animateTo(0f)
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            CryptoItemWallet(cryptoModel = cryptoModel, amount = amount, sum = sum, onClick = onClick)
        }
    }
}

@Composable
fun CryptoItemWallet(
    modifier: Modifier = Modifier,
    cryptoModel: CryptoListingsModel,
    amount : String,
    sum : String,
    onClick: () -> Unit
) {

    androidx.compose.material3.ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.paddings.extraMedium)
            .clickable { onClick() },
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.paddings.extraMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                AsyncImage(
                    modifier = modifier.size(35.dp),
                    model = cryptoModel.icon,
                    error = painterResource(id = com.example.common_ui.R.drawable.ic_error_24),
                    contentDescription = null,
                )

                CryptoNameSymbolPricePercent(
                    symbol = cryptoModel.symbol,
                    name = cryptoModel.name,
                    price = formatPriceString(cryptoModel.price),
                    percent = cryptoModel.percentage
                )
            }

            Column(
                modifier = modifier.padding(vertical = MaterialTheme.paddings.medium),
            ) {
                Text(
                    modifier = modifier.padding(bottom = MaterialTheme.paddings.extraSmall),
                    text = formatPriceString(sum),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = amount,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun BalanceHistoryItem(
    modifier: Modifier = Modifier,
    balance : String,
    date : String,
    profit : String,
    percentOrName : String,
    isPercent : Boolean
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.paddings.extraMedium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = modifier.padding(vertical = MaterialTheme.paddings.extraSmall)
        ) {
            Text(
                modifier = modifier.padding(bottom = MaterialTheme.paddings.small),
                text = formatPriceString(balance),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(
            modifier = modifier.padding(vertical = MaterialTheme.paddings.extraSmall),
        ) {
            Text(
                modifier = modifier.padding(bottom = MaterialTheme.paddings.extraSmall),
                text = formatPriceString(profit),
                style = MaterialTheme.typography.titleMedium
            )
            if (isPercent) Text(
                text = "Пополнение: $percentOrName",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
             else PercentageText(percent = percentOrName)
        }
    }
}

@Composable
@PreviewLightDark
fun ItemPreview() {
    val model = CryptoListingsModel(
        1,
        "BTC",
        "bitcoin",
        "Bitcoin",
        "https://static.coinstats.app/coins/1650455588819.png",
        "69.48916",
        "0.18",
        percentageOneHour = "",
        percentageOneWeek = "",
        totalSupply = "",
        marketCap = "",
        redditUrl = "",
        twitterUrl = ""
    )
    PreviewWrapper {
        Column {
            CryptoItem(
                cryptoModel = model,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
            CryptoItemSmall(cryptoModel = model) {}
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
            CryptoItemWallet(cryptoModel = model, amount = "0.3465", sum = "1225.683") {}
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
            BalanceHistoryItem(
                balance = "2421.026",
                date = "11.11.2024",
                profit = "75.982",
                percentOrName = "bitcoin",
                isPercent = true
            )
        }
    }
}