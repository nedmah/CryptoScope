package com.example.common_ui.composable.chart

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.EmptyLineShader
import me.bytebeats.views.charts.line.render.line.ILineDrawer
import me.bytebeats.views.charts.line.render.line.ILineShader
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.point.IPointDrawer
import me.bytebeats.views.charts.line.render.xaxis.IXAxisDrawer
import me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.IYAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Composable
fun CustomLineChart(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    pointDrawer: IPointDrawer = FilledCircularPointDrawer(),
    lineDrawer: ILineDrawer = SolidLineDrawer(),
    lineShader: ILineShader = EmptyLineShader,
    xAxisDrawer: IXAxisDrawer = SimpleXAxisDrawer(),
    yAxisDrawer: IYAxisDrawer = SimpleYAxisDrawer(),
    horizontalOffset: Float = 5F,
) {
    // Поиск минимальной и максимальной точки
    val minPoint = lineChartData.points.minByOrNull { it.value }
    val maxPoint = lineChartData.points.maxByOrNull { it.value }

    LineChart(
        lineChartData = lineChartData,
        modifier = modifier,
        animation = animation,
        pointDrawer = pointDrawer,
        lineDrawer = lineDrawer,
        lineShader = lineShader,
        xAxisDrawer = object : IXAxisDrawer by xAxisDrawer {
            override fun drawXAxisLabels(
                drawScope: DrawScope,
                canvas: Canvas,
                drawableArea: Rect,
                labels: List<Any?>
            ) {
                val filteredLabels = lineChartData.points.mapIndexed { index, point ->
                    if (point == minPoint || point == maxPoint) {
                        labels[index]
                    } else {
                        null // Не отображаем остальные лейблы
                    }
                }.filterNotNull()

                // Отрисовываем только отфильтрованные лейблы
                xAxisDrawer.drawXAxisLabels(
                    drawScope = drawScope,
                    canvas = canvas,
                    drawableArea = drawableArea,
                    labels = filteredLabels
                )
            }
        },
        yAxisDrawer = yAxisDrawer,
        horizontalOffset = horizontalOffset
    )
}