@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.common_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common_ui.R
import com.example.common_ui.clearFocusOnKeyboardDismiss


@Composable
fun CryptoSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onCancel: () -> Unit = { onValueChange("") },
    enabled: Boolean = true,
    showKeyboard: Boolean = false,
    isFocused: MutableState<Boolean> = remember { mutableStateOf(false) },
    focusRequester: FocusRequester = remember { FocusRequester() },
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = {
        Icon(
            painter = painterResource(id = R.drawable.ic_search_24),
            contentDescription = null
        )
    },
    isError: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = searchTrailingContent(
        onClick = onCancel,
        value = value,
        enabled = enabled,
    ),
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.medium,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
){
    if (showKeyboard) {
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            }
            .clearFocusOnKeyboardDismiss(),
        enabled = enabled,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}


@Composable
fun searchTrailingContent(
    value : String,
    enabled : Boolean,
    onClick : () -> Unit,
    trailingIcon : @Composable () -> Unit = {
        IconButton(onClick = onClick) {
            Icon(painter = painterResource(id = R.drawable.ic_cancel_24), contentDescription = null)
        }
    }
) : @Composable (() -> Unit)? =
    when{
        value.isNotEmpty() && enabled -> {
            {
                trailingIcon()
            }
        }
        else -> null
    }



@Composable
@PreviewLightDark
fun SearchBarPreview() {

//    val leadingContent: @Composable () -> Unit = {
//        Icon(
//            painter = painterResource(id = R.drawable.ic_search_24),
//            contentDescription = null
//        )
//    }

    PreviewWrapper {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CryptoSearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = "Enabled",
                onValueChange = {},
            )
            CryptoSearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = "",
//                leadingIcon = leadingContent,
                onValueChange = {},
                placeholder = { Text(text = "Placeholder") }
            )

            CryptoSearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = "Error",
                onValueChange = {},
                label = { Text(text = "Label") },
                supportingText = { Text(text = "An error occurs") },
                isError = true
            )
            CryptoSearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Disabled") },
                enabled = false
            )
        }
    }
}