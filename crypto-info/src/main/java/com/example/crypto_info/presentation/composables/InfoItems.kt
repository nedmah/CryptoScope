package com.example.crypto_info.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.common_ui.composable.DashedDivider
import com.example.common_ui.composable.ElevatedCard
import com.example.common_ui.composable.PreviewWrapper
import com.example.common_ui.theme.extraColor
import com.example.common_ui.theme.paddings
import com.example.common_ui.theme.spacers
import com.example.core.util.formatPriceString

@Composable
fun ExternalUrl(
    modifier: Modifier = Modifier,
    url: String,
    imageId: Int,
    onClick: (String) -> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.paddings.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier.size(40.dp),
            painter = painterResource(id = imageId),
            contentDescription = null
        )

        val annotatedText = buildAnnotatedString {
            append(url)
            addStyle(
                style = SpanStyle(
                    color = MaterialTheme.extraColor.hyperlink,
                    textDecoration = TextDecoration.Underline
                ), start = 0, end = url.length
            )
        }

        ClickableText(
            modifier = modifier,
            text = annotatedText,
            style = MaterialTheme.typography.bodyLarge,
            onClick = { offset ->
                if (offset in 0..url.length) {
                    onClick(url) // Pass the URL to the onClick function
                }
            }
        )
    }
}

@Composable
fun CryptoInfoItem(
    modifier: Modifier = Modifier,
    text: String,
    value: String
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.paddings.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(modifier = Modifier.width(120.dp)) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Text(
            modifier = modifier,
            text = formatPriceString(value),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun CryptoComparisonItem(
    title: String,
    text1: String,
    text2: String,
    background: Color,
    modifier: Modifier = Modifier,
    formatPrice: (String) -> String = { it }, // Функция форматирования (можно передать извне)
    circleColor1: Color = MaterialTheme.extraColor.chart, // Цвет кружка для text1
    circleColor2: Color = MaterialTheme.extraColor.secondChart // Цвет кружка для text2
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = background)
            .padding(
                vertical = MaterialTheme.paddings.small,
                horizontal = MaterialTheme.paddings.medium
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val needsFormatting = title.equals("Price", ignoreCase = true) ||
                title.equals("Total supply", ignoreCase = true) ||
                title.equals("Market cap", ignoreCase = true)

        val text1Formatted = if (needsFormatting) {
            text1.toDoubleOrNull()?.let { formatPrice(it.toString()) } ?: text1
        } else text1

        val text2Formatted = if (needsFormatting) {
            text2.toDoubleOrNull()?.let { formatPrice(it.toString()) } ?: text2
        } else text2

        val text1Value = text1.toDoubleOrNull()
        val text2Value = text2.toDoubleOrNull()

        val textColor1 = when {
            title.equals("Rank", ignoreCase = true) && text1Value != null && text2Value != null -> {
                if (text1Value < text2Value) MaterialTheme.extraColor.positive
                else MaterialTheme.colorScheme.onSurface
            }

            text1Value != null && text2Value != null && text1Value > text2Value -> MaterialTheme.extraColor.positive
            else -> MaterialTheme.colorScheme.onSurface
        }

        val textColor2 = when {
            title.equals("Rank", ignoreCase = true) && text1Value != null && text2Value != null -> {
                if (text2Value < text1Value) MaterialTheme.extraColor.positive
                else MaterialTheme.colorScheme.onSurface
            }

            text1Value != null && text2Value != null && text2Value > text1Value -> MaterialTheme.extraColor.positive
            else -> MaterialTheme.colorScheme.onSurface
        }

        Row(
            modifier = modifier
                .align(Alignment.Start)
                .padding(bottom = MaterialTheme.paddings.extraMedium),
        ) {
            Text(
                modifier = modifier,
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier,
                    text = text1Formatted,
                    color = textColor1,
                    style = MaterialTheme.typography.bodyMedium
                )
                AddNameCircle(color = circleColor1, title = title)
            }

            DashedDivider(
                thickness = 0.2.dp,
                color = Color.White,
                phase = 0f, // Фаза для пунктирного эффекта
                intervals = floatArrayOf(10f, 8f), // Интервалы для пунктиров
                modifier = Modifier
                    .height(20.dp)
                    .align(Alignment.CenterVertically)
                    .width(0.2.dp)
            )
            Row(
                modifier = modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier,
                    text = text2Formatted,
                    color = textColor2,
                    style = MaterialTheme.typography.bodyMedium
                )
                AddNameCircle(color = circleColor2, title = title)
            }
        }

    }
}

@Composable
@NonRestartableComposable
private fun AddNameCircle(color: Color, title: String) {
    if (title.equals("Name", ignoreCase = true)) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .padding(start = 4.dp)
                .clip(CircleShape)
                .background(color)
        )
    }
}

@Composable
@PreviewLightDark
fun CryptoInfoPreview() {
    PreviewWrapper {
        Column {
            ExternalUrl(
                url = "fawdawdasdawdasdawd",
                imageId = com.example.common_ui.R.drawable.reddit_logo,
                onClick = {}
            )
            ExternalUrl(
                url = "fawdawdasdawdasdawd",
                imageId = com.example.common_ui.R.drawable.x_logo,
                onClick = {}
            )

            CryptoInfoItem(text = "marketCap", value = "1615651351")
            CryptoInfoItem(text = "TotalSupply", value = "859845356156")

            CryptoComparisonItem(
                title = "Name",
                text1 = "btc",
                text2 = "fad",
                background = Color.Gray
            )

        }
    }
}