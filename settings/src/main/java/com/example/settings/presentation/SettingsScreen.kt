package com.example.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.common_ui.theme.paddings
import com.example.settings.navigation.Routes

@Composable
fun SettingsScreen(
    action: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        SettingsItem(
            stringResource(com.example.common_ui.R.string.comparison),
            com.example.common_ui.R.drawable.ic_comparison_24,
            Routes.CryptoComparisonScreen.name
        ),
        SettingsItem(
            stringResource(com.example.common_ui.R.string.accounts),
            com.example.common_ui.R.drawable.ic_list_24,
            Routes.CryptoAccountsScreen.name
        ),
        SettingsItem(
            stringResource(com.example.common_ui.R.string.theme),
            com.example.common_ui.R.drawable.ic_lightmode_24
        ),
        SettingsItem(
            stringResource(com.example.common_ui.R.string.currency),
            title = "USD",
            route = Routes.CryptoCurrencyScreen.name
        ),
        SettingsItem(
            stringResource(com.example.common_ui.R.string.info),
            com.example.common_ui.R.drawable.ic_info_24,
            Routes.CryptoAboutScreen.name
        ),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier.padding(
                top = MaterialTheme.paddings.xxLarge,
                bottom = MaterialTheme.paddings.xxLarge
            ),
            text = "Настройки",
            style = MaterialTheme.typography.headlineSmall
        )


        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .clip(RoundedCornerShape(20.dp))
        ) {
            LazyColumn {
                items(count = items.size) { index ->
                    val item = items[index]
                    ListItem(
                        modifier = modifier.clickable {
                            item.route?.let { route ->
                                action(route)
                            }
                        },
                        headlineContent = { Text(text = item.name) },
                        leadingContent = {
                            item.imageId?.let {
                                Icon(
                                    painter = painterResource(id = it),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = null
                                )
                            } ?: Text(
                                text = item.title!!,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },

                        trailingContent = {
                            Icon(
                                painter = painterResource(id = com.example.common_ui.R.drawable.ic_short_arrow_right),
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null
                            )
                        }
                    )
                    HorizontalDivider()
                }
            }

        }
    }
}