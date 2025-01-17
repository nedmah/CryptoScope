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
import androidx.compose.runtime.MutableState
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
import com.example.core.util.formatPriceString
import com.example.core.util.formatTimestamp
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
    val textMeasurer = rememberTextMeasurer()
    val selectedPoint = remember { mutableStateOf<Offset?>(null) }
    val selectedValue = remember { mutableStateOf<String?>(null) }
    val selectedDate = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(lineChartData.points) {
        transitionAnimation.snapTo(0F)
        transitionAnimation.animateTo(1F, animationSpec = animation)
        selectedPoint.value = null
        selectedValue.value = null
        selectedDate.value = null
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
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
                        selectedValue.value = formatPriceString(tappedPoint.value.toString())
                        val date = tappedPoint.label.toLongOrNull() ?: 0L
                        selectedDate.value = formatTimestamp(date, milli = true)
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
    lineShader: List<ILineShader> = listOf(EmptyLineShader),
    xAxisDrawer: CryptoLabelDrawer,
    horizontalOffset: Float = 5F,
    priceColor: Color = MaterialTheme.colorScheme.onBackground,
    dotColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    check(horizontalOffset in 0F..25F) {
        "Horizontal Offset is the percentage offset from side, and must be between 0 and 25, included."
    }

    val transitionAnimation = remember(lineChartDataList) {
        Animatable(initialValue = 0F)
    }

    // Состояние для выбранной точки
    val selectedPoint = remember { mutableStateOf<Offset?>(null) }
    val selectedValue = remember { mutableStateOf<String?>(null) }
    val selectedDate = remember { mutableStateOf<String?>(null) }
    val selectedGraphIndex = remember { mutableStateOf<Int?>(null) }
    val textMeasurer = rememberTextMeasurer()

    LaunchedEffect(lineChartDataList) {
        transitionAnimation.snapTo(0F)
        transitionAnimation.animateTo(1F, animationSpec = animation)
        selectedPoint.value = null
        selectedValue.value = null
        selectedDate.value = null
        selectedGraphIndex.value = null
    }


    val allPoints = lineChartDataList.flatMap { it.points }
    val globalMinY = allPoints.minOf { it.value }
    val globalMaxY = allPoints.maxOf { it.value }

    val globalRange = globalMaxY - globalMinY

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    // Рассчитываем область отрисовки
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
                    // Поиск ближайшей точки среди всех графиков
                    var minDistance = Float.MAX_VALUE
                    var closestPoint: LineChartData.Point? = null
                    var selectedChartIndex: Int? = null

                    lineChartDataList.forEachIndexed { chartIndex, lineChartData ->
                        lineChartData.points.forEach { point ->
                            val pointLocation = computePointLocation(
                                drawableArea = drawableArea,
                                lineChartData = lineChartData,
                                point = point,
                                index = lineChartData.points.indexOf(point),
                                globalMinY = globalMinY,
                                globalRange = globalRange
                            )
                            val distance = pointLocation.getDistanceTo(tapOffset)
                            if (distance < minDistance) {
                                minDistance = distance
                                closestPoint = point
                                selectedChartIndex = chartIndex
                            }
                        }
                    }

                    // Обновляем состояние
                    if (closestPoint != null) {
                        selectedPoint.value = computePointLocation(
                            drawableArea = drawableArea,
                            lineChartData = lineChartDataList.first { it.points.contains(closestPoint) },
                            point = closestPoint!!,
                            index = lineChartDataList.first { it.points.contains(closestPoint) }
                                .points.indexOf(closestPoint),
                            globalMinY = globalMinY,
                            globalRange = globalRange
                        )
                        selectedValue.value = formatPriceString(closestPoint!!.value.toString())
                        val date = closestPoint!!.label.toLongOrNull() ?: 0L
                        selectedDate.value = formatTimestamp(date, milli = true)
                    } else {
                        selectedPoint.value = null
                        selectedValue.value = null
                        selectedDate.value = null
                        selectedGraphIndex.value = null
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
                        transitionProgress = transitionAnimation.value,
                        globalMinY = globalMinY,
                        globalRange = globalRange
                    )
                )
                lineShader[index].fillLine(
                    drawScope = this,
                    canvas = canvas,
                    fillPath = computeFillPath(
                        drawableArea = chartDrawableArea,
                        lineChartData = lineChartData,
                        transitionProgress = transitionAnimation.value,
                        globalMinY = globalMinY,
                        globalRange = globalRange
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
                                index = index,
                                globalMinY = globalMinY,
                                globalRange = globalRange
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

                    // Отрисовка выбранной точки и её данных
                    selectedPoint.value?.let { pointOffset ->
                        drawCircle(center = pointOffset, color = dotColor, radius = 7f)
                        val offset = if (pointOffset.x > 700) Offset(pointOffset.x - 350, pointOffset.y)
                        else pointOffset
                        drawText(
                            textMeasurer = textMeasurer,
                            text = "${selectedValue.value ?: ""} (${selectedGraphIndex.value ?: ""})",
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
}
