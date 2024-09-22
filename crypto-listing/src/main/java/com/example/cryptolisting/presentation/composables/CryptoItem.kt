package com.example.cryptolisting.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.common_ui.composable.PercentageText
import com.example.common_ui.composable.PreviewWrapper
import com.example.common_ui.theme.model.Paddings
import com.example.core.util.formatPrice
import com.example.cryptolisting.R
import com.example.cryptolisting.domain.model.CryptoListingsModel


@Composable
fun CryptoItem(
    modifier: Modifier = Modifier,
    cryptoModel: CryptoListingsModel
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth().padding(vertical = Paddings.extraMedium),
        shape = RoundedCornerShape(20)
    ) {
        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = Paddings.extraMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row{
                AsyncImage(
                    modifier = modifier.size(35.dp),
                    model = cryptoModel.icon,
                    error = painterResource(id = com.example.common_ui.R.drawable.ic_error_24),
                    contentDescription = null,
                )

                Column(
                    modifier = modifier.padding(start = Paddings.medium),
                ){
                    Text(
                        modifier = modifier.padding(bottom = Paddings.small),
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
                modifier = modifier.padding(vertical = Paddings.medium),
                horizontalAlignment = Alignment.End
            ) {
                PercentageText(
                    percent = cryptoModel.percentage
                )
                Text(
                    text = formatPrice(cryptoModel.price),
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
            )
        )
    }
}