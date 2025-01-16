package com.example.common_ui.composable.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import me.bytebeats.views.charts.line.LineChartData

internal fun computeDrawableArea(
    xAxisDrawableArea: Rect,
    yAxisDrawableArea: Rect,
    size: Size,
    offset: Float
): Rect {
    val horizontalOffset = xAxisDrawableArea.width * offset / 100F
    return Rect(
        left = 0f,   //whole width
        top = 0F,
        bottom = xAxisDrawableArea.top,
        right = size.width - horizontalOffset
    )
}

internal fun computeXAxisDrawableArea(
    yAxisWidth: Float,
    labelHeight: Float,
    size: Size
): Rect {
    val top = size.height - labelHeight
    return Rect(
        left = yAxisWidth,
        top = top,
        right = size.width,
        bottom = size.height
    )
}

internal fun computeXAxisLabelsDrawableArea(
    xAxisDrawableArea: Rect,
    offset: Float
): Rect {
    val horizontalOffset = xAxisDrawableArea.width * offset / 100F
    return Rect(
        left = xAxisDrawableArea.left + horizontalOffset,
        top = xAxisDrawableArea.top,
        right = xAxisDrawableArea.right - horizontalOffset,
        bottom = xAxisDrawableArea.bottom
    )
}

internal fun Density.computeYAxisDrawableArea(
    xAxisLabelSize: Float,
    size: Size
): Rect {
    val right =
        50.dp.toPx().coerceAtMost(size.width * 10F / 100F) // 50dp or 10% of chart view width
    return Rect(
        left = 0F,
        top = 0F,
        right = right,
        bottom = size.height - xAxisLabelSize
    )
}

internal fun computePointLocation(
    drawableArea: Rect,
    lineChartData: LineChartData,
    point: LineChartData.Point,
    index: Int
): Offset {
    val dx = index.toFloat() / (lineChartData.points.size - 1)
    val dy = (point.value - lineChartData.minY) / lineChartData.yRange
    return Offset(
        x = dx * drawableArea.width + drawableArea.left,
        y = drawableArea.height - dy * drawableArea.height
    )
}

/**
 * for comparison
 */
internal fun computePointLocation(
    drawableArea: Rect,
    lineChartData: LineChartData,
    point: LineChartData.Point,
    index: Int,
    globalMinY: Float,
    globalRange: Float,
    scaleFactor: Float = 0.6f // scale (from 0 - 0%, 1 - 100%)
): Offset {
    val dx = index.toFloat() / (lineChartData.points.size - 1)
    val dy = (point.value - globalMinY) / globalRange
    val scaledHeight = drawableArea.height * scaleFactor
    val verticalOffset = (drawableArea.height - scaledHeight) / 2

    return Offset(
        x = dx * drawableArea.width + drawableArea.left,
        y = drawableArea.bottom - (dy * scaledHeight + verticalOffset)
    )
}
/**
 * for comparison
 */
internal fun computeLinePath(
    drawableArea: Rect,
    lineChartData: LineChartData,
    transitionProgress: Float,
    globalMinY: Float,
    globalRange: Float
): Path = Path().apply {
    var prePointLocation: Offset? = null
    lineChartData.points.forEachIndexed { index, point ->
        withProgress(index, lineChartData, transitionProgress) { progress ->
            val pointLocation = computePointLocation(
                drawableArea = drawableArea,
                lineChartData = lineChartData,
                point = point,
                index = index,
                globalMinY = globalMinY,
                globalRange = globalRange
            )
            if (index == 0) {
                moveTo(pointLocation.x, pointLocation.y)
            } else {
                if (progress <= 1F) {
                    val preX = prePointLocation?.x ?: 0F
                    val preY = prePointLocation?.y ?: 0F
                    val tx = (pointLocation.x - preX) * progress + preX
                    val ty = (pointLocation.y - preY) * progress + preY
                    lineTo(tx, ty)
                } else {
                    lineTo(pointLocation.x, pointLocation.y)
                }
            }
            prePointLocation = pointLocation
        }
    }
}
/**
 * for comparison
 */
internal fun computeFillPath(
    drawableArea: Rect,
    lineChartData: LineChartData,
    transitionProgress: Float,
    globalMinY: Float,
    globalRange: Float
): Path = Path().apply {
    moveTo(drawableArea.left, drawableArea.bottom)
    var prePointX: Float? = null
    var prePointLocation: Offset? = null
    lineChartData.points.forEachIndexed { index, point ->
        withProgress(index, lineChartData, transitionProgress) { progress ->
            val pointLocation = computePointLocation(
                drawableArea = drawableArea,
                lineChartData = lineChartData,
                point = point,
                index = index,
                globalMinY = globalMinY,
                globalRange = globalRange
            )
            if (index == 0) {
                lineTo(drawableArea.left, pointLocation.y)
                lineTo(pointLocation.x, pointLocation.y)
            } else {
                prePointX = if (progress <= 1F) {
                    val preX = prePointLocation?.x ?: 0F
                    val preY = prePointLocation?.y ?: 0F
                    val tx = (pointLocation.x - preX) * progress + preX
                    val ty = (pointLocation.y - preY) * progress + preY
                    lineTo(tx, ty)
                    tx
                } else {
                    lineTo(pointLocation.x, pointLocation.y)
                    pointLocation.x
                }
            }
            prePointLocation = pointLocation
        }
    }
    prePointX?.let {
        lineTo(it, drawableArea.bottom)
        lineTo(drawableArea.left, drawableArea.bottom)
    } ?: lineTo(drawableArea.left, drawableArea.bottom)
}


internal fun withProgress(
    index: Int,
    lineChartData: LineChartData,
    transitionProgress: Float,
    progressListener: (progress: Float) -> Unit
) {
    val size = lineChartData.points.size
    val toIndex = (size * transitionProgress).toInt() + 1
    if (index == toIndex) {
        val sizeF = lineChartData.points.size.toFloat()
        val divider = 1F / sizeF
        val down = (index - 1) * divider
        progressListener((transitionProgress - down) / divider)
    } else if (index < toIndex) {
        progressListener(1F)
    }
}

internal fun computeLinePath(
    drawableArea: Rect,
    lineChartData: LineChartData,
    transitionProgress: Float
): Path = Path().apply {
    var prePointLocation: Offset? = null
    lineChartData.points.forEachIndexed { index, point ->
        withProgress(index, lineChartData, transitionProgress) { progress ->
            val pointLocation = computePointLocation(drawableArea, lineChartData, point, index)
            if (index == 0) {
                moveTo(pointLocation.x, pointLocation.y)
            } else {
                if (progress <= 1F) {
                    val preX = prePointLocation?.x ?: 0F
                    val preY = prePointLocation?.y ?: 0F
                    val tx = (pointLocation.x - preX) * progress + preX
                    val ty = (pointLocation.y - preY) * progress + preY
                    lineTo(tx, ty)
                } else {
                    lineTo(pointLocation.x, pointLocation.y)
                }
            }
            prePointLocation = pointLocation
        }
    }
}

internal fun computeFillPath(
    drawableArea: Rect,
    lineChartData: LineChartData,
    transitionProgress: Float
): Path = Path().apply {
    moveTo(drawableArea.left, drawableArea.bottom)
    var prePointX: Float? = null
    var prePointLocation: Offset? = null
    lineChartData.points.forEachIndexed { index, point ->
        withProgress(index, lineChartData, transitionProgress) { progress ->
            val pointLocation = computePointLocation(drawableArea, lineChartData, point, index)
            if (index == 0) {
                lineTo(drawableArea.left, pointLocation.y)
                lineTo(pointLocation.x, pointLocation.y)
            } else {
                prePointX = if (progress <= 1F) {
                    val preX = prePointLocation?.x ?: 0F
                    val preY = prePointLocation?.y ?: 0F
                    val tx = (pointLocation.x - preX) * progress + preX
                    val ty = (pointLocation.y - preY) * progress + preY
                    lineTo(tx, ty)
                    tx
                } else {
                    lineTo(pointLocation.x, pointLocation.y)
                    pointLocation.x
                }
            }
            prePointLocation = pointLocation
        }
    }
    prePointX?.let {
        lineTo(it, drawableArea.bottom)
        lineTo(drawableArea.left, drawableArea.bottom)
    } ?: lineTo(drawableArea.left, drawableArea.bottom)
}