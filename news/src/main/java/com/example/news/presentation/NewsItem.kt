package com.example.news.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.common_ui.composable.PreviewWrapper
import com.example.common_ui.shimmerEffect
import com.example.common_ui.theme.paddings
import com.example.core.util.truncateText

@Composable
fun CryptoNewsItem(
    title: String,
    source: String,
    imageUrl: String,
    date: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(modifier = modifier
        .fillMaxWidth()
        .height(128.dp)
        .clickable {
            onClick()
        }
    ) {
        Row(
            modifier = modifier.padding(MaterialTheme.paddings.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {

            AsyncImage(
                modifier = modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                filterQuality = FilterQuality.Medium,
                contentScale = ContentScale.Crop,
                model = imageUrl,
                error = painterResource(id = com.example.common_ui.R.drawable.ic_error_24),
                contentDescription = null,
            )

            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .padding(start = MaterialTheme.paddings.small),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, style = MaterialTheme.typography.titleSmall, maxLines = 2)
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Text(
                        modifier = modifier,
                        text = truncateText(source, 20),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }


        }
    }
}

@Composable
fun CryptoNewsItemPlaceholder(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(128.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth().padding(MaterialTheme.paddings.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {

            Box(
                modifier = modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shimmerEffect(),
            )

            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .padding(vertical = MaterialTheme.paddings.medium,),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = modifier
                        .width(40.dp)
                        .height(15.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .shimmerEffect()
                )
                Box(
                    modifier = modifier
                        .width(120.dp)
                        .height(15.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .shimmerEffect()
                )
            }

        }

    }
}

@Composable
@PreviewLightDark
fun NewsItemPreview() {
    PreviewWrapper {
        CryptoNewsItem(
            title = "Bitcoin reaches new pick with Donald Trump as a president of US ",
            source = "BBC News",
            imageUrl = "https://coin-turk.com/wp-content/uploads/2024/11/bitcoin-202.jpg",
            date = "20.12.2024"
        ) {

        }
    }
}