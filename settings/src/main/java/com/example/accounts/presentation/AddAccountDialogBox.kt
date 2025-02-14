package com.example.accounts.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.common_ui.composable.CryptoDialogBox
import com.example.common_ui.composable.CryptoOutlinedTextField
import com.example.common_ui.composable.DropdownMenuField
import com.example.common_ui.theme.paddings
import com.example.common_ui.theme.spacers
import com.example.settings.R


@Composable
fun AccountsDialogBox(
    address: String,
    onAddressChange: (String) -> Unit,
    options1: List<String>,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {


    CryptoDialogBox(
        content = {
            Column {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DropdownMenuField(
                        label = stringResource(id = com.example.common_ui.R.string.blockchain),
                        selectedOption = selectedOption,
                        onOptionSelected = onOptionSelected,
                        options = options1
                    )
//                    Text(
//                        text = "Необязательно",
//                        style = MaterialTheme.typography.labelMedium,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
                }

                CryptoOutlinedTextField(
                    value = address,
                    onValueChange = onAddressChange,
                    label = { Text(text = stringResource(id = com.example.common_ui.R.string.address)) },
                )
            }
        },
        title = stringResource(id = com.example.common_ui.R.string.add_account),
        onConfirm = onConfirm,
        onCancel = onDismissRequest
    )
}