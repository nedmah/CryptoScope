package com.example.settings.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.common_ui.composable.CryptoDialogBox
import com.example.common_ui.composable.CryptoFilledButton
import com.example.common_ui.composable.CryptoOutlinedTextField
import com.example.common_ui.theme.paddings
import com.example.core.util.isValidApiKey

@Composable
fun AddApiDialogBox(
    initialApi: String,
    onDismissRequest: () -> Unit,
    onDeleteApi: () -> Unit,
    onConfirm: (String) -> Unit,
    onClickUrl: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {

    var apiInput by remember { mutableStateOf(initialApi) }
    val isValidKey = remember(apiInput) { isValidApiKey(apiInput) }

    CryptoDialogBox(
        content = {
            Column {
                AnnotatedTitle { url ->
                    onClickUrl(url)
                }

                CryptoOutlinedTextField(
                    value = apiInput,
                    onValueChange = { apiInput = it },
                    isError = !isValidKey && apiInput.isNotBlank(),
                    placeholder = { Text(text = stringResource(id = com.example.common_ui.R.string.key)) },
                    label = { Text(text = stringResource(id = com.example.common_ui.R.string.key)) },
                    supportingText = {
                        if (!isValidKey && apiInput.isNotBlank()) {
                            Text(text = stringResource(id = com.example.common_ui.R.string.error_api_key))
                        }
                    }
                )

                CryptoFilledButton(
                    modifier = modifier,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    onClick = { onDeleteApi() }
                ) {
                    Text(text = stringResource(com.example.common_ui.R.string.delete))
                }
            }
        },
        title = stringResource(id = com.example.common_ui.R.string.add_api),
        onConfirm = { onConfirm(apiInput) },
        onCancel = onDismissRequest,
        enabledButton = isValidKey || apiInput.isBlank(),
    )
}

@Composable
fun AnnotatedTitle(
    onClickUrl: (String) -> Unit,
){
    val annotatedString = buildAnnotatedString {
        val fullText = stringResource(id = com.example.common_ui.R.string.add_api_info)
        append(fullText)

        val siteWord = "openapi.coinstats.app"
        val startIndex = fullText.indexOf(siteWord)
        val endIndex = if (startIndex >= 0) startIndex + siteWord.length else -1

        if (startIndex >= 0) {
            addStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = "https://openapi.coinstats.app",
                start = startIndex,
                end = endIndex
            )
        }
    }

    ClickableText(
        text = annotatedString,
        modifier = Modifier,
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.secondary),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    onClickUrl(annotation.item)
                }
        }
    )
}