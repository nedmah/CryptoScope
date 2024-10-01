package com.example.crypto_info.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.common_ui.composable.PreviewWrapper
import com.example.common_ui.theme.paddings
import com.example.common_ui.theme.spacers


@Composable
fun IntervalButtonGroup(
    modifier: Modifier = Modifier,
    onIntervalChanged: (TimeIntervals) -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small)
    )
    {

        var selectedColor by remember { mutableIntStateOf(0) }

        TimeIntervals.entries.forEachIndexed { index, interval ->

            val (backgroundColor, fontColor) = if (index == selectedColor) {
                Pair(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
            } else {
                Pair(Color.Transparent, MaterialTheme.colorScheme.onSurfaceVariant)
            }

            IntervalButton(
                modifier = modifier.background(
                    color = backgroundColor, shape = RoundedCornerShape(14.dp)
                ),
                color = fontColor,
                interval = interval
            ) {
                if (index != selectedColor) selectedColor = index
                onIntervalChanged(interval)
            }
        }
    }
}


@Composable
private fun IntervalButton(
    modifier: Modifier = Modifier,
    color: Color,
    interval: TimeIntervals,
    onClick: () -> Unit
) {

    Text(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.paddings.small,
                horizontal = MaterialTheme.paddings.medium
            )
            .clickable {
                onClick()
            },
        textAlign = TextAlign.Center,
        color = color,
        text = interval.text.uppercase(),
        style = MaterialTheme.typography.bodySmall
    )
}


@Composable
@PreviewLightDark
fun ButtonGroupPreview(){
    PreviewWrapper {
        IntervalButtonGroup {

        }
    }
}