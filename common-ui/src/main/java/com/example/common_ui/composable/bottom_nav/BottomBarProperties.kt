package com.example.common_ui.composable.bottom_nav

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class BottomBarProperties(
    val backgroundColor: Color = Blue,
    val indicatorColor: Color = Color.White.copy(alpha = 0.2F),
    val iconTintColor: Color = Color.Black,
    val iconTintActiveColor: Color = Color.White,
    val textActiveColor: Color = Color.White,
    val cornerRadius: Dp = 8.dp,
    val textStyle: TextStyle = TextStyle.Default
)
