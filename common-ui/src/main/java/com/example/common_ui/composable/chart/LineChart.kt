package com.example.common_ui.composable.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.common_ui.theme.extraColor
import com.example.core.util.formatTimestamp
import com.example.core.util.formatTimestampSimple
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.EmptyLineShader
import me.bytebeats.views.charts.line.render.line.ILineDrawer
import me.bytebeats.views.charts.line.render.line.ILineShader
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.EmptyPointDrawer.drawPoint
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.point.IPointDrawer
import me.bytebeats.views.charts.simpleChartAnimation
import me.bytebeats.views.charts.toLegacyInt
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

private fun Offset.getDistanceTo(other: Offset): Float {
    return sqrt((x - other.x).pow(2) + (y - other.y).pow(2))
}

@Composable
fun CryptoLineChart(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier,
    priceColor : Color = Color.Black,
    dotColor : Color = MaterialTheme.extraColor.chart,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    pointDrawer: IPointDrawer = FilledCircularPointDrawer(),
    lineDrawer: ILineDrawer = SolidLineDrawer(),
    lineShader: ILineShader = EmptyLineShader,
    xAxisDrawer: CryptoLabelDrawer,
    horizontalOffset: Float = 5F,
) {
    check(horizontalOffset in 0F..25F) {
        "Horizontal Offset is the percentage offset from side, and must be between 0 and 25, included."
    }
    val transitionAnimation = remember(lineChartData.points) {
        Animatable(initialValue = 0F)
    }

    LaunchedEffect(lineChartData.points) {
        transitionAnimation.snapTo(0F)
        transitionAnimation.animateTo(1F, animationSpec = animation)
    }

    val textMeasurer = rememberTextMeasurer()
    val selectedPoint = remember { mutableStateOf<Offset?>(null) }
    val selectedValue = remember { mutableStateOf<String?>(null) }
    val selectedDate = remember { mutableStateOf<String?>(null) }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit){
                detectTapGestures { tapOffset ->
                    val drawableArea = computeDrawableArea(
                        xAxisDrawableArea = computeXAxisDrawableArea(
                            yAxisWidth = 50.dp.toPx(),
                            labelHeight = xAxisDrawer.requireHeight(CanvasDrawScope()),
                            size = size.toSize()
                        ),
                        yAxisDrawableArea = computeYAxisDrawableArea(
                            xAxisLabelSize = xAxisDrawer.requireHeight(CanvasDrawScope()),
                            size = size.toSize()
                        ),
                        size = size.toSize(),
                        offset = horizontalOffset
                    )
                    val tappedPoint = lineChartData.points.minByOrNull { point ->
                        val pointLocation = computePointLocation(
                            drawableArea = drawableArea,
                            lineChartData = lineChartData,
                            point = point,
                            index = lineChartData.points.indexOf(point) // Передаём индекс
                        )
                        pointLocation.getDistanceTo(tapOffset)
                    }
                    if (tappedPoint != null) {
                        selectedPoint.value = computePointLocation(
                            drawableArea = drawableArea,
                            lineChartData = lineChartData,
                            point = tappedPoint,
                            index = lineChartData.points.indexOf(tappedPoint)
                        )
                        selectedValue.value = tappedPoint.value.toString()
                        println(tappedPoint.label)
                        selectedDate.value = formatTimestamp(tappedPoint.label.toLong())
                    } else {
                        selectedPoint.value = null
                        selectedValue.value = null
                        selectedDate.value = null
                    }
                }
            }
    ) {
        drawIntoCanvas { canvas ->
            val yAxisDrawableArea = computeYAxisDrawableArea(
                xAxisLabelSize = xAxisDrawer.requireHeight(this),
                size = size
            )
            val xAxisDrawableArea = computeXAxisDrawableArea(
                yAxisWidth = yAxisDrawableArea.width,
                labelHeight = xAxisDrawer.requireHeight(this),
                size = size
            )

            val chartDrawableArea = computeDrawableArea(
                xAxisDrawableArea = xAxisDrawableArea,
                yAxisDrawableArea = yAxisDrawableArea,
                size = size,
                offset = horizontalOffset
            )

            lineDrawer.drawLine(
                drawScope = this,
                canvas = canvas,
                linePath = computeLinePath(
                    drawableArea = chartDrawableArea,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                )
            )
            lineShader.fillLine(
                drawScope = this,
                canvas = canvas,
                fillPath = computeFillPath(
                    drawableArea = chartDrawableArea,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                )
            )
            if (lineChartData.points.isNotEmpty()) {
                val maxPrice = lineChartData.points.maxOf { it.value }
                val minPrice = lineChartData.points.minOf { it.value }
                val maxPriceIndex = lineChartData.points.indexOfFirst { it.value == maxPrice }
                val minPriceIndex = lineChartData.points.indexOfFirst { it.value == minPrice }

                lineChartData.points.forEachIndexed { index, point ->
                    withProgress(
                        index = index,
                        lineChartData = lineChartData,
                        transitionProgress = transitionAnimation.value
                    ) {
                        val pointLocation = computePointLocation(
                            drawableArea = chartDrawableArea,
                            lineChartData = lineChartData,
                            point = point,
                            index = index
                        )
                        pointDrawer.drawPoint(
                            drawScope = this,
                            canvas = canvas,
                            center = pointLocation
                        )

                        if (index in listOf(minPriceIndex, maxPriceIndex)) {
                            xAxisDrawer.drawXAxisLabels(
                                drawScope = this,
                                canvas = canvas,
                                label = point.value,
                                pointLocation = pointLocation,
                                drawableArea = xAxisDrawableArea,
                                isHighestPrice = index == maxPriceIndex
                            )
                        }
                    }
                }


                selectedPoint.value?.let { pointOffset ->
                    val offset = if (pointOffset.x > 700) Offset(pointOffset.x - 350, pointOffset.y)
                    else pointOffset

                    drawCircle(center = pointOffset - Offset(0.dp.toPx(), 5.dp.toPx()), color = dotColor, radius = 7f)
                    drawText(
                        textMeasurer = textMeasurer,
                        text = selectedValue.value ?: "",
                        topLeft = offset,
                        style = TextStyle(color = priceColor)
                    )
                    drawText(
                        textMeasurer = textMeasurer,
                        text = selectedDate.value ?: "",
                        topLeft = offset - Offset(0.dp.toPx(), 15.dp.toPx()),
                        style = TextStyle(color = priceColor)
                    )
                }

            }
        }
    }
}



@Composable
fun CryptoLineChartsComparison(
    lineChartDataList: List<LineChartData>, // Список данных для нескольких графиков
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    pointDrawer: IPointDrawer = FilledCircularPointDrawer(),
    lineDrawer: List<ILineDrawer> = listOf(SolidLineDrawer()),
    lineShader: ILineShader = EmptyLineShader,
    xAxisDrawer: CryptoLabelDrawer,
    horizontalOffset: Float = 5F,
) {
    check(horizontalOffset in 0F..25F) {
        "Horizontal Offset is the percentage offset from side, and must be between 0 and 25, included."
    }

    val transitionAnimation = remember(lineChartDataList) {
        Animatable(initialValue = 0F)
    }

    LaunchedEffect(lineChartDataList) {
        transitionAnimation.snapTo(0F)
        transitionAnimation.animateTo(1F, animationSpec = animation)
    }

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        drawIntoCanvas { canvas ->
            val yAxisDrawableArea = computeYAxisDrawableArea(
                xAxisLabelSize = xAxisDrawer.requireHeight(this),
                size = size
            )
            val xAxisDrawableArea = computeXAxisDrawableArea(
                yAxisWidth = yAxisDrawableArea.width,
                labelHeight = xAxisDrawer.requireHeight(this),
                size = size
            )

            val chartDrawableArea = computeDrawableArea(
                xAxisDrawableArea = xAxisDrawableArea,
                yAxisDrawableArea = yAxisDrawableArea,
                size = size,
                offset = horizontalOffset
            )

            var maxPrice = 0f
            var minPrice = 0f
            var maxPriceIndex = 0
            var minPriceIndex = 0
            lineChartDataList.forEachIndexed { index,lineChartData ->

                lineDrawer[index].drawLine(
                    drawScope = this,
                    canvas = canvas,
                    linePath = computeLinePath(
                        drawableArea = chartDrawableArea,
                        lineChartData = lineChartData,
                        transitionProgress = transitionAnimation.value
                    )
                )
                lineShader.fillLine(
                    drawScope = this,
                    canvas = canvas,
                    fillPath = computeFillPath(
                        drawableArea = chartDrawableArea,
                        lineChartData = lineChartData,
                        transitionProgress = transitionAnimation.value
                    )
                )
                if (lineChartData.points.isNotEmpty()) {
                    maxPrice = max(maxPrice,lineChartData.points.maxOf { it.value })
                    minPrice = min(minPrice,lineChartData.points.minOf { it.value })
                    maxPriceIndex = lineChartData.points.indexOfFirst { it.value == maxPrice }
                    minPriceIndex = lineChartData.points.indexOfFirst { it.value == minPrice }

                    lineChartData.points.forEachIndexed { index, point ->
                        withProgress(
                            index = index,
                            lineChartData = lineChartData,
                            transitionProgress = transitionAnimation.value
                        ) {
                            val pointLocation = computePointLocation(
                                drawableArea = chartDrawableArea,
                                lineChartData = lineChartData,
                                point = point,
                                index = index
                            )
                            pointDrawer.drawPoint(
                                drawScope = this,
                                canvas = canvas,
                                center = pointLocation
                            )

                            if (index in listOf(minPriceIndex, maxPriceIndex)) {
                                xAxisDrawer.drawXAxisLabels(
                                    drawScope = this,
                                    canvas = canvas,
                                    label = point.value,
                                    pointLocation = pointLocation,
                                    drawableArea = xAxisDrawableArea,
                                    isHighestPrice = index == maxPriceIndex
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
