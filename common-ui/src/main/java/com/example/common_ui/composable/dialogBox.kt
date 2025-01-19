package com.example.common_ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.common_ui.theme.extraColor

@Composable
fun CryptoDialogBox(
    content : @Composable ColumnScope.() -> Unit,
    title : String,
    onConfirm : () -> Unit,
    onCancel : () -> Unit,
    modifier: Modifier = Modifier,
    confirmButtonText: String = "Confirm",
    cancelButtonText: String = "Cancel"
){
    AlertDialog(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        onDismissRequest = onCancel,
        title = { Text(title) },
        text = {
            Column {
                content()
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.extraColor.positive,
                    contentColor = Color.White
                )
            ) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.extraColor.negative,
                    contentColor = Color.White
                )
            ) {
                Text(cancelButtonText)
            }
        }
    )
}