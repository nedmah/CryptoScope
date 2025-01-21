package com.example.accounts.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.common_ui.composable.CryptoDialogBox
import com.example.common_ui.composable.CryptoOutlinedTextField
import com.example.common_ui.composable.DropdownMenuField


@Composable
fun AccountsDialogBox(
    address : String,
    onAddressChange : (String) -> Unit,
    options1: List<String>,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {


    CryptoDialogBox(
        content = {
            Column {

                DropdownMenuField(
                    label = "Blockchain",
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected,
                    options = options1
                )

                CryptoOutlinedTextField(
                    value = address,
                    onValueChange = onAddressChange,
                    label = { Text(text = "address")},
                )
            }
        },
        title = "Add account",
        onConfirm = onConfirm,
        onCancel = onDismissRequest
    )
}