package com.example.common_ui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.common_ui.theme.CryptoScopeTheme
import com.example.common_ui.theme.model.Spacers


@Composable
fun CryptoFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = CryptoButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    FilledTonalButton(
        modifier = modifier,
        colors = colors,
        contentPadding = contentPadding,
        enabled = enabled,
        onClick = onClick
    ) {
        content()
    }
}

@Composable
fun CryptoTonalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(),
    contentPadding: PaddingValues = CryptoButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    FilledTonalButton(
        modifier = modifier,
        colors = colors,
        contentPadding = contentPadding,
        enabled = enabled,
        onClick = onClick
    ) {
        content()
    }
}


@Composable
fun CryptoTonalIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(),
    contentPadding: PaddingValues = CryptoButtonDefaults.ButtonWithIconContentPadding,
    leadingIcon: @Composable () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        content = {
            leadingIcon()
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            content()
        }
    )
}


object CryptoButtonDefaults {
    private val buttonHorizontalPadding = 24.dp
    private val buttonVerticalPadding = 10.dp
    private val buttonWithIconHorizontalStartPadding = 16.dp

    val ContentPadding =
        PaddingValues(
            start = buttonHorizontalPadding,
            top = buttonVerticalPadding,
            end = buttonHorizontalPadding,
            bottom = buttonVerticalPadding
        )

    val ButtonWithIconContentPadding =
        PaddingValues(
            start = buttonWithIconHorizontalStartPadding,
            top = buttonVerticalPadding,
            end = buttonHorizontalPadding,
            bottom = buttonVerticalPadding
        )

    @Composable
    fun pinKeyboardColors() = IconButtonDefaults.filledIconButtonColors(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}


@Preview
@Composable
private fun CryptoFilledButtonPreview() {
    PreviewWrapper {
        Row {
            CryptoFilledButton(onClick = {}) {
                Text(text = "Enabled")
            }
            Spacer(modifier = Modifier.width(8.dp))
            CryptoFilledButton(onClick = {}, enabled = false) {
                Text(text = "Disabled")
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CryptoTonalButtonPreview() {
    PreviewWrapper {
            Row {
                CryptoTonalButton(onClick = {}) {
                    Text(text = "enabled")
                }
                Spacer(modifier = Modifier.width(Spacers.small))
                CryptoTonalButton(onClick = {}, enabled = false) {
                    Text(text = "disabled")
                }
            }
        }
}