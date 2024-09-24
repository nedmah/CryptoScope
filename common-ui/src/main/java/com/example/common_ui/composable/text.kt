package com.example.common_ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.common_ui.R

@Composable
fun PercentageText(
    percent : String,
    modifier: Modifier = Modifier
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
            style = MaterialTheme.typography.bodyMedium
        )
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