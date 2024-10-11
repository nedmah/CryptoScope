package com.example.crypto_info.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
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
    onClick : (String) -> Unit,
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
        }
    }
}