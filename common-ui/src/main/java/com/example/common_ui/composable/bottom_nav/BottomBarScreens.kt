package com.example.common_ui.composable.bottom_nav

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarScreens(
    val id : Int,
    val route: String,
    val name: String,
    val icon: Int,
    val imageVector: ImageVector? = null
){
    constructor(id : Int, route: String, name: String, icon: Int) : this(id, route, name, icon, null)
    constructor(id : Int, route: String, name: String, imageVector: ImageVector) : this(id, route, name, 0,imageVector)
}
