package com.example.crypto_info.presentation.comparison

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.common_ui.composable.CryptoDialogBox
import com.example.common_ui.composable.DropdownMenuField
import com.example.common_ui.theme.extraColor
import com.example.crypto_info.R

@Composable
fun CompareDialog(
    options1: List<String>,
    options2: List<String>,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    selectedOption1: String,
    onOption1Selected: (String) -> Unit,
    selectedOption2: String,
    onOption2Selected: (String) -> Unit
) {

    CryptoDialogBox(
        content = {
            Column {
                DropdownMenuField(
                    label = "Option 1",
                    selectedOption = selectedOption1,
                    onOptionSelected = onOption1Selected,
                    options = options1
                )
                DropdownMenuField(
                    label = "Option 2",
                    selectedOption = selectedOption2,
                    onOptionSelected = onOption2Selected,
                    options = options2
                )
            }
        },
        title = stringResource(id = com.example.common_ui.R.string.compare),
        onConfirm = onConfirm,
        onCancel = onDismissRequest
    )
}

