package com.example.common_ui.composable.bottom_nav

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.common_ui.R
import com.example.common_ui.composable.PreviewWrapper
import com.example.common_ui.theme.extraColor

@Composable
fun CryptoBottomBar(
    navController: NavHostController,
    bottomNavigationItems: List<BottomBarScreens>,
    initialIndex: MutableState<Int>,
    bottomBarProperties: BottomBarProperties = BottomBarProperties(
        backgroundColor = MaterialTheme.colorScheme.background,
        iconTintColor = MaterialTheme.extraColor.navIconColor,
        iconTintActiveColor = MaterialTheme.extraColor.wallet,
        textActiveColor = MaterialTheme.extraColor.wallet,
        textStyle = MaterialTheme.typography.bodySmall
    ),
    onSelectItem: (BottomBarScreens) -> Unit
) {
    Surface(
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(bottomBarProperties.backgroundColor)
                .height(105.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavigationBar(
                containerColor = bottomBarProperties.backgroundColor,
                modifier = Modifier.fillMaxWidth()
            ) {
                bottomNavigationItems.forEachIndexed { index, screen ->
                    val isSelected = index == initialIndex.value

                    // Анимируем высоту текста
                    val textHeight by animateDpAsState(
                        targetValue = if (isSelected) 20.dp else 0.dp, // Высота текста
                        label = "TextHeight"
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                if (isSelected) return@clickable

                                initialIndex.value = index
                                onSelectItem(screen)
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (screen.imageVector != null) {
                            Icon(
                                imageVector = screen.imageVector,
                                contentDescription = screen.name,
                                tint = if (isSelected) bottomBarProperties.iconTintActiveColor else bottomBarProperties.iconTintColor
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = screen.icon),
                                contentDescription = screen.name,
                                tint = if (isSelected) bottomBarProperties.iconTintActiveColor else bottomBarProperties.iconTintColor
                            )
                        }

                        // Анимируем видимость текста
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier
                                .height(textHeight) // Высота зависит от анимации
                                .animateContentSize() // Плавный переход высоты
                        ) {
                            if (isSelected) {
                                Text(
                                    text = screen.name,
                                    color = bottomBarProperties.textActiveColor,
                                    maxLines = 1,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    style = bottomBarProperties.textStyle
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
fun previewBottomBar(){
    PreviewWrapper {
        val bottomNavItems = listOf(
            BottomBarScreens(
                0,
                "Routes.CryptoNewsScreen.name",
                stringResource(id = R.string.news),
                R.drawable.ic_news_24
            ),
            BottomBarScreens(
                1,
                "Routes.CryptoListingsScreen.name",
                stringResource(id = R.string.market),
                R.drawable.ic_market_24
            ),
            BottomBarScreens(
                2,
                "Routes.CryptoWalletScreen.name",
                stringResource(id = R.string.wallet),
                R.drawable.ic_wallet_24
            )
        )
        val index = remember { mutableStateOf(0) }
        CryptoBottomBar(
            navController = rememberNavController(),
            bottomNavigationItems = bottomNavItems,
            initialIndex = index
        ) {
            Log.i("SELECTED_ITEM", "onCreate: Selected Item ${it.name}")
        }

    }
}