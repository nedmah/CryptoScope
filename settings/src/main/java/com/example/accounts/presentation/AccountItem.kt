package com.example.accounts.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.common_ui.composable.PreviewWrapper
import com.example.common_ui.theme.extraColor
import com.example.common_ui.theme.paddings
import com.example.core.util.truncateText
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun AccountItem(
    address: String,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    isSelected: Boolean,
    image: String?,
    blockchain: String?,
    modifier: Modifier = Modifier
) {

    var contextMenuWidth by remember { mutableFloatStateOf(0f) }
    val offset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()

    var isVisible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isVisible,
        exit = shrinkVertically() + fadeOut(),
    ) {
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
                Icon(
                    modifier = modifier.clickable {
                        onDelete()
                        isVisible = false
                        scope.launch {
                            offset.animateTo(0f)
                        }
                    },
                    painter = painterResource(id = com.example.common_ui.R.drawable.ic_delete_24),
                    contentDescription = null
                )
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
                AccountNonSwipeableItem(
                    address = address,
                    onClick = onClick,
                    isSelected = isSelected,
                    image = image,
                    blockchain = blockchain
                )
            }
        }
    }

}


@Composable
private fun AccountNonSwipeableItem(
    address: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    image: String?,
    blockchain: String?,
    modifier: Modifier = Modifier
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
                .padding(
                    horizontal = MaterialTheme.paddings.extraMedium,
                    vertical = MaterialTheme.paddings.small
                ),
//            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = modifier.padding(end = MaterialTheme.paddings.medium)
            ) {
                blockchain?.let {
                    AsyncImage(
                        modifier = modifier
                            .clip(CircleShape)
                            .size(30.dp),
                        model = image,
                        error = painterResource(id = com.example.common_ui.R.drawable.ic_image_placeholder_48),
                        contentDescription = null,
                    )
                    Text(text = it, style = MaterialTheme.typography.titleMedium)
                }
            }

            Text(
                modifier = modifier.weight(1f),
                text = blockchain?.let { truncateText(address, 20) } ?: truncateText(address, 35),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )

                AnimatedVisibility(visible = isSelected){
                    Icon(
                        modifier = modifier.weight(1f),
                        painter = painterResource(id = com.example.common_ui.R.drawable.ic_select_16),
                        tint = MaterialTheme.extraColor.chart,
                        contentDescription = null
                    )
                }
        }
    }


}

@Composable
@PreviewLightDark
fun ItemPreview() {
    PreviewWrapper{
        Column {
            AccountItem(
                address = "abcdefghijklmnopqrstuxyzawdsadawdasdw",
                onClick = { /*TODO*/ },
                onDelete = { /*TODO*/ },
                isSelected = false,
                image = "",
                blockchain = null
            )
            AccountItem(
                address = "abcdefghijklmnopqrstuxyzaaaaaaaa",
                onClick = { /*TODO*/ },
                onDelete = { /*TODO*/ },
                isSelected = true,
                image = "",
                blockchain = "TON"
            )
        }
    }
}