package com.example.cryptolisting.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.common_ui.composable.PercentageText
import com.example.common_ui.composable.PreviewWrapper
import com.example.common_ui.theme.paddings
import com.example.core.util.formatPriceString
import com.example.cryptolisting.domain.model.CryptoListingsModel


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
            .clickable { onClick() } ,
        shape = RoundedCornerShape(20)
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
                "0.18"
            ),
            onClick = {}
        )
    }
}