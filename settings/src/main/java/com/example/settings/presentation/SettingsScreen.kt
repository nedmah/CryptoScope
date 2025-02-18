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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common_ui.theme.paddings
import com.example.settings.R

@Composable
fun SettingsScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navigate: (String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(factory = getViewModelFactory())
) {

    val state = viewModel.settingsState.collectAsState().value

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
            text = stringResource(id = com.example.common_ui.R.string.settings),
            style = MaterialTheme.typography.headlineSmall
        )


        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .clip(RoundedCornerShape(20.dp))
        ) {
            LazyColumn {
                items(count = state.items.size) { index ->
                    val item = state.items[index]

                    ListItem(
                        modifier = modifier.clickable {
                            if(item.route != null) navigate(item.route)
                            else {
                                viewModel.onEvent(SettingsEvents.ChangeTheme)
                            }
                        },
                        headlineContent = { Text(text = stringResource(id = item.nameId)) },
                        leadingContent = {
                            item.imageId?.let {
                                    Icon(
                                        painter = painterResource(id = it),
                                        tint = MaterialTheme.colorScheme.primary,
                                        contentDescription = null
                                    )
                            } ?: Text(
                                text = item.title!!.uppercase(),
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
