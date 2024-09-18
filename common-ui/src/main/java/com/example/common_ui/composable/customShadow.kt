package com.example.common_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.customShadow(
    color: Color = Color.Black,
    alpha: Float = 0f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    scaleX: Float = 1f,
    scaleY: Float = 1f,
    widthOffset: Dp = 0.dp,
    heightOffset: Dp = 0.dp,
): Modifier = drawBehind {
    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            left = 0f,
            top = 0f,
            right = this.size.width * scaleX + widthOffset.toPx(),
            bottom = this.size.height * scaleY + heightOffset.toPx(),
            radiusX = cornersRadius.toPx(),
            radiusY = cornersRadius.toPx(),
            paint = paint
        )
    }
}

@Preview
@Composable
private fun Preview() = Box(
    modifier = Modifier
        .size(size = 50.dp)
        .background(color = Color.White)
) {
    Box(
        modifier = Modifier
            .size(size = 40.dp)
            .customShadow(
                alpha = 0.5f,
                shadowBlurRadius = 2.dp,
                offsetY = 4.dp,
                offsetX = 4.dp
            )
            .background(color = Color.Red)
    )
}