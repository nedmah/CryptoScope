package com.example.crypto_info.presentation.comparison

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.common_ui.composable.PreviewWrapper
import com.example.common_ui.theme.paddings
import com.example.crypto_info.R
import com.example.crypto_info.presentation.composables.CryptoInfoItem
import com.example.crypto_info.presentation.composables.ExternalUrl

@Composable
@NonRestartableComposable
fun ComparisonHeader(
    onBack: () -> Unit,
    onCompare : () -> Unit,
    iconOne : String,
    iconTwo : String,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onBack()
                    },
                painter = painterResource(id = com.example.common_ui.R.drawable.ic_back_24),
                contentDescription = null
            )

            Icon(
                modifier = modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onCompare()
                },
                painter = painterResource(id = com.example.common_ui.R.drawable.ic_comparison_24),
                contentDescription = null
            )
        }

        Text(
            modifier = modifier.padding(vertical = MaterialTheme.paddings.medium),
            text = "Сравнение",
            style = MaterialTheme.typography.headlineSmall
        )

        Row {
            AsyncImage(
                modifier = modifier
                    .size(35.dp)
                    .clip(CircleShape),
                model = iconOne,
                placeholder = painterResource(id = com.example.common_ui.R.drawable.ic_image_placeholder_48),
                error = painterResource(id = com.example.common_ui.R.drawable.ic_image_placeholder_48),
                contentDescription = null,
            )

            Text(
                modifier = modifier.padding(horizontal = MaterialTheme.paddings.extraMedium).align(Alignment.CenterVertically),
                text = "vs",
                style = MaterialTheme.typography.bodyMedium
            )

            AsyncImage(
                modifier = modifier
                    .size(35.dp)
                    .clip(CircleShape),
                model = iconTwo,
                placeholder = painterResource(id = com.example.common_ui.R.drawable.ic_image_placeholder_48),
                error = painterResource(id = com.example.common_ui.R.drawable.ic_image_placeholder_48),
                contentDescription = null,
            )
        }
    }
}

@Composable
@PreviewLightDark
fun CryptoInfoPreview() {
    PreviewWrapper {
        Column {
            ComparisonHeader(
                onBack = { /*TODO*/ },
                onCompare = { /*TODO*/ },
                iconOne = "",
                iconTwo = ""
            )
        }
    }
}