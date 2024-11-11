package com.example.common_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.common_ui.R
import com.example.common_ui.theme.extraColor
import com.example.common_ui.theme.paddings


@Composable
fun PercentageTextCard(
    modifier: Modifier = Modifier,
    percent : String,
    style: TextStyle = MaterialTheme.typography.titleMedium,
){
    Box(
        modifier = modifier.background(
            color = MaterialTheme.extraColor.percentageCard,
            shape = RoundedCornerShape(14.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        PercentageText(
            modifier = modifier.padding(MaterialTheme.paddings.extraSmall),
            percent = percent,
            style = style
        )
    }
}


@Composable
fun PercentageText(
    modifier: Modifier = Modifier,
    percent : String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
){

    val isPositive = !percent.contains('-')
    val value = if (isPositive) percent else percent.replace("-","")

    val iconRes = if (isPositive) R.drawable.ic_up_24 else R.drawable.ic_down_24
    val color = if (isPositive) Color(0xFF18DD7D) else MaterialTheme.colorScheme.error


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Box(modifier = modifier.width(20.dp), contentAlignment = Alignment.Center)
        {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = color
            )
        }

        Text(
            modifier = modifier,
            text = value,
            color = color,
            style = style
        )
    }
}

@Composable
fun CryptoNameSymbol(
    modifier: Modifier = Modifier,
    padding : Dp = MaterialTheme.paddings.small,
    symbol : String,
    name : String
){
    Column(
        modifier = modifier.padding(start = MaterialTheme.paddings.medium),
    ) {
        Text(
            modifier = modifier.padding(bottom = padding),
            text = symbol,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun CryptoNameSymbolPricePercent(
    modifier: Modifier = Modifier,
    padding : Dp = MaterialTheme.paddings.small,
    symbol : String,
    name : String,
    price : String,
    percent: String
){
    Column(
        modifier = modifier.padding(start = MaterialTheme.paddings.medium),
    ) {
        Text(
            modifier = modifier.padding(bottom = padding),
            text = "$name($symbol)",
            style = MaterialTheme.typography.titleMedium
        )

        Row{
            Text(
                text = price,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            PercentageText(percent = percent, style = MaterialTheme.typography.bodySmall,)
        }
    }
}

@Composable
@PreviewLightDark
fun TextPreview(){
    PreviewWrapper {
        Column {
            PercentageText(percent = "-0.25")
            PercentageText(percent = "0.25")
        }
    }
}