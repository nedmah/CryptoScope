@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.common_ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.common_ui.theme.paddings

@Composable
fun CryptoScrollableScaffold(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    footer: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    CryptoBasicScrollableScaffold(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        snackbarHost = snackbarHost,
    ) {
        Column {
            Column(
                Modifier
                    .weight(1.0F)
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding)
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = MaterialTheme.paddings.medium,
                    ),
                content = content
            )
            footer()
        }
    }
}

@Composable
fun CryptoScrollableLazyScaffold(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    floatingActionButton: @Composable (() -> Unit) = {},
    content: LazyListScope.() -> Unit
) {
    CryptoBasicScrollableScaffold(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        floatingActionButton = floatingActionButton,
    ) {
        LazyColumn(
            modifier = Modifier.padding(contentPadding),
            content = content,
            contentPadding = PaddingValues(
                top = it.calculateTopPadding(),
                bottom = MaterialTheme.paddings.medium,
            ),
        )
    }
}

@Composable
fun CryptoBasicScrollableScaffold(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    topBarColors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ),
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    CryptoBasicScaffold(
        modifier = modifier,
        floatingActionButton = floatingActionButton,
        topBar = {
            TopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = {
                    Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                        actions()
                    }
                },
                colors = topBarColors,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        scrollBehavior = scrollBehavior,
        content = content
    )
}

@Composable
fun CryptoBasicScaffold(
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = scrollBehavior?.nestedScrollConnection?.run(modifier::nestedScroll)
            ?: modifier,
        topBar = topBar,
        containerColor = MaterialTheme.colorScheme.surface,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        bottomBar = bottomBar,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        content = content
    )
}