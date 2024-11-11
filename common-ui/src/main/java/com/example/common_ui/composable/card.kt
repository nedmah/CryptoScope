package com.example.common_ui.composable

import android.graphics.Point
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common_ui.theme.extraColor
import com.example.common_ui.theme.paddings
import com.example.common_ui.theme.spacers
import com.example.core.util.extensions.boxShadow


@Composable
fun WalletCard(
    modifier: Modifier = Modifier,
    height: Dp = 200.dp,
    gradientColors: List<Color> = listOf(
        MaterialTheme.extraColor.wallet,
        MaterialTheme.extraColor.chart
    ),
    contentColor: Color = Color.White,
    shadowColor: Color = MaterialTheme.extraColor.chart,
    balance: String,
    profit: String,
    percentage: String,
    navigate : () -> Unit,
) {


    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clickable {
                navigate
            }
            .background(
                brush = Brush.linearGradient(gradientColors),
                shape = RoundedCornerShape(12.dp)
            )
            .boxShadow(
                color = shadowColor,
                borderRadius = 12.dp,
                blurRadius = 24.dp,
                offsetX = MaterialTheme.paddings.extraMedium,
                offsetY = height,
                spread = (-6).dp
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    horizontal = MaterialTheme.paddings.medium,
                    vertical = MaterialTheme.paddings.large
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(text = "Баланс", color = contentColor)
                    Spacer(modifier = modifier.height(MaterialTheme.spacers.extraSmall))
                    Text(text = balance, color = contentColor, style = MaterialTheme.typography.titleLarge)
                }
                Column {
                    Text(text = "Прибыль за сегодня", color = contentColor)
                    Spacer(modifier = modifier.height(MaterialTheme.spacers.extraSmall))
                    Text(text = profit, color = contentColor, style = MaterialTheme.typography.titleLarge)
                }
            }
            PercentageTextCard(modifier = modifier.align(Alignment.Bottom), percent = percentage)
        }
    }
}


@Composable
fun ElevatedCard(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    contentArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier.clip(MaterialTheme.shapes.large),
        tonalElevation = 1.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = containerColor)
                .padding(MaterialTheme.paddings.medium),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = contentArrangement,
        ) {
            content()
        }
    }
}

@PreviewLightDark
@Composable
fun cardsPreview() {
    PreviewWrapper {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            ElevatedCard {
                Text(text = "fawdawdawdaw")
            }

            Spacer(modifier = Modifier.size(MaterialTheme.paddings.large))

            WalletCard(balance = "\$2,549.370", profit = "\$75.982", percentage = "0.12"){

            }

            Spacer(modifier = Modifier.size(MaterialTheme.paddings.xxLarge))
        }
    }
}