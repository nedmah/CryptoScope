package com.example.common_ui.composable

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.common_ui.theme.CryptoScopeTheme

@Composable
fun PreviewWrapper(content: @Composable () -> Unit) {
    CryptoScopeTheme {
        Surface(
            content = content
        )
    }
}