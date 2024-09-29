package com.example.common_ui.composable.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

@Suppress("LongParameterList")
interface CryptoLabelDrawer {


    fun requireHeight(drawScope: DrawScope): Float = 0F

    fun drawXAxisLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        label: Any?,
        pointLocation: Offset,
        drawableArea: Rect,
        isHighestPrice: Boolean
    )
}