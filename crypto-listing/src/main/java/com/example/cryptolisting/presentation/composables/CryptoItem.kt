package com.example.cryptolisting.presentation.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.common_ui.composable.FavoriteIcon
import com.example.common_ui.composable.PercentageText
import com.example.common_ui.composable.PreviewWrapper
import com.example.common_ui.theme.paddings
import com.example.core.util.formatPriceString
import com.example.cryptolisting.domain.model.CryptoListingsModel
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt


@Composable
fun CryptoSwipeableItem(
    modifier: Modifier = Modifier,
    cryptoModel: CryptoListingsModel,
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
            FavoriteIcon(modifier = modifier.padding(start = MaterialTheme.paddings.medium)) {
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

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.paddings.extraMedium)
            .clickable { onClick() },
//        shape = RoundedCornerShape(20)
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

                Column(
                    modifier = modifier.padding(start = MaterialTheme.paddings.medium),
                ) {
                    Text(
                        modifier = modifier.padding(bottom = MaterialTheme.paddings.small),
                        text = cryptoModel.symbol,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = cryptoModel.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
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
@PreviewLightDark
fun ItemPreview() {
    PreviewWrapper {
        CryptoItem(
            cryptoModel = CryptoListingsModel(
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
            ),
            onClick = {}
        )
    }
}