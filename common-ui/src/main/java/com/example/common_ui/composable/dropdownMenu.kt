package com.example.common_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.common_ui.R

@Composable
fun DropdownMenuField(
    label: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    options: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Box(
            modifier = modifier.align(Alignment.Start)
        ) {
            TextButton(
                onClick = { expanded = true },
                modifier = modifier
            ) {
                Text(
                    selectedOption.ifEmpty { stringResource(id = R.string.select) },
                    textDecoration = TextDecoration.Underline
                )
            }

            DropdownMenu(
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.background),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Box(modifier = Modifier.size(width = 100.dp, height = 300.dp)) {
                    LazyColumn {
                        items(options.size) { index ->
                            val option = options[index]
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    onOptionSelected(option)
                                    expanded = false
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }

        }
    }
}