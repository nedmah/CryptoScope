package com.example.common_ui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.common_ui.theme.CryptoScopeTheme

@Composable
fun CryptoSpinner(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    size: Dp = CryptoSpinnerDefaults.buttonSize,
    color: Color = MaterialTheme.colorScheme.onPrimary,
) {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(paddingValues)
            .size(size = size)
            .then(modifier),
        strokeWidth = 3.dp,
        color = color
    )
}

object CryptoSpinnerDefaults {
    val buttonSize = 24.dp
    val alertDialogSize = 18.dp
    val dropdownSize = 16.dp
}

