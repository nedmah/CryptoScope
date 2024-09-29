package com.example.common_ui.composable.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.util.formatChartPrice
import me.bytebeats.views.charts.AxisLabelFormatter
import me.bytebeats.views.charts.toLegacyInt

private const val Y_OFFSET = 20f


data class CryptoLabelsDrawer(
    val labelTextSize: TextUnit = 12.sp,
    val lowestTextColor: Color = Color.Black,
    val highestTextColor: Color = Color.Black,
    val drawLabelEvery: Int = 1,// draw label text every $drawLabelEvery, like 1, 2, 3 and so on.
    val axisLineThickness: Dp = 1.dp,
    val axisLineColor: Color = Color.Black,
    val axisLabelFormatter: AxisLabelFormatter = { value -> formatChartPrice("$value") }
) : CryptoLabelDrawer {

    private val mLabelTextArea: Float? = null

    private val mPaintLowest by lazy {
        android.graphics.Paint().apply {
            textAlign = android.graphics.Paint.Align.CENTER
            color = lowestTextColor.toLegacyInt()
        }
    }

    private val mPaintHighest by lazy {
        android.graphics.Paint().apply {
            textAlign = android.graphics.Paint.Align.CENTER
            color = highestTextColor.toLegacyInt()
        }
    }

    override fun requireHeight(drawScope: DrawScope): Float = with(drawScope) {
        1.5f * (labelTextSize.toPx() + axisLineThickness.toPx())
    }


    override fun drawXAxisLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        label: Any?,
        pointLocation: Offset,
        drawableArea: Rect,
        isHighestPrice: Boolean
    ) {


        val textPaint = if (isHighestPrice) {
            mPaintHighest
        } else {
            mPaintLowest
        }
        val labelValue = axisLabelFormatter(label)
        val bounds = android.graphics.Rect()
        textPaint.getTextBounds(labelValue, 0, labelValue.length, bounds)

        with(drawScope) {
            val labelPaint = textPaint.apply {
                textSize = labelTextSize.toPx()
                textAlign = android.graphics.Paint.Align.CENTER
            }


            val xCenter = when {
                pointLocation.x - bounds.width() / 2 <= 0 -> { // First point on the chart
                    bounds.width().toFloat() / 2
                }

                pointLocation.x + bounds.width() / 2 >= canvas.nativeCanvas.width -> { // Last point on the chart
                    canvas.nativeCanvas.width - bounds.width().toFloat() / 2
                }

                else -> {
                    pointLocation.x
                }
            }
            val yCenter = if (isHighestPrice) {
                pointLocation.y - labelTextHeight(drawScope) / 2
            } else {
                pointLocation.y + labelTextHeight(drawScope) / 2 + Y_OFFSET
            }
            canvas.nativeCanvas.drawText(labelValue, xCenter, yCenter, labelPaint)
        }
    }

    private fun labelTextHeight(drawScope: DrawScope): Float = with(drawScope) {
        mLabelTextArea ?: (1.5F * labelTextSize.toPx())
    }
}

