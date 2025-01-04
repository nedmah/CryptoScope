package com.example.common_ui.composable.chart

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.common_ui.theme.extraColor
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.GradientLineShader
import me.bytebeats.views.charts.line.render.line.ILineDrawer
import me.bytebeats.views.charts.line.render.line.ILineShader
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.EmptyPointDrawer
import me.bytebeats.views.charts.line.render.point.IPointDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Composable
fun CustomLineChart(
    modifier: Modifier = Modifier,
    lineChartData: LineChartData,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    pointDrawer: IPointDrawer = EmptyPointDrawer, //FilledCircularPointDrawer()
    lineDrawer: ILineDrawer = SolidLineDrawer(
        thickness = 2.dp,
        color = MaterialTheme.extraColor.chart
    ),
    lineShader: ILineShader = GradientLineShader(
        colors = listOf(
            MaterialTheme.extraColor.chartGradient,
            Color.Transparent
        )
    ),
    labelDrawer: CryptoLabelDrawer = CryptoLabelsDrawer(
        lowestTextColor = MaterialTheme.extraColor.negative,
        highestTextColor = MaterialTheme.extraColor.positive
    ),
    horizontalOffset: Float = 0F,
) {

    CryptoLineChart(
        lineChartData = lineChartData,
        modifier = modifier.fillMaxWidth(),
        animation = animation,
        pointDrawer = pointDrawer,
        lineDrawer = lineDrawer,
        lineShader = lineShader,
        xAxisDrawer = labelDrawer,
        priceColor = MaterialTheme.colorScheme.onBackground,
        horizontalOffset = horizontalOffset,
    )
}

@Composable
fun CompareLineCharts(
    modifier: Modifier = Modifier,
    lineChartData: List<LineChartData>,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    pointDrawer: IPointDrawer = EmptyPointDrawer, //FilledCircularPointDrawer()
    lineDrawer: List<ILineDrawer> = listOf(
        SolidLineDrawer(
            thickness = 2.dp,
            color = MaterialTheme.extraColor.chart
        ),
        SolidLineDrawer(
            thickness = 2.dp,
            color = MaterialTheme.extraColor.secondChart
        )
    ),
    lineShader: List<ILineShader> = listOf(
        GradientLineShader(
            colors = listOf(
                MaterialTheme.extraColor.chartGradient,
                Color.Transparent
            )
        ),
        GradientLineShader(
            colors = listOf(
                MaterialTheme.extraColor.secondGradient,
                Color.Transparent
            )
        )
    ),
    labelDrawer: CryptoLabelDrawer = CryptoLabelsDrawer(
        lowestTextColor = MaterialTheme.extraColor.negative,
        highestTextColor = MaterialTheme.extraColor.positive
    ),
    horizontalOffset: Float = 0F,
) {

    CryptoLineChartsComparison(
        lineChartDataList = lineChartData,
        modifier = modifier.fillMaxWidth(),
        animation = animation,
        pointDrawer = pointDrawer,
        lineDrawer = lineDrawer,
        lineShader = lineShader,
        xAxisDrawer = labelDrawer,
        horizontalOffset = horizontalOffset,
    )
}