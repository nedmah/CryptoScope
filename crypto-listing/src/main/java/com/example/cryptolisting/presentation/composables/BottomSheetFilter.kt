package com.example.cryptolisting.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.common_ui.composable.BottomDialog
import com.example.common_ui.theme.paddings
import com.example.cryptolisting.presentation.Filters

@Composable
fun FilterBottomSheet(
    onFilterSelected: (Filters) -> Unit,
    onDismiss: () -> Unit
) {
    val filterOptions = Filters.entries.toList()

    var selectedItem by remember { mutableIntStateOf(0) }

    BottomDialog(
        showDialog = true,
        onDismiss = { onDismiss() }
    ) {
        Column {
            Text(text = "Sorting", modifier = Modifier.align(Alignment.CenterHorizontally))
            HorizontalDivider()
            filterOptions.forEachIndexed { index,filter ->
                FilterOptionItem(
                    filter = filter,
                    isSelected = index == selectedItem,
                    onFilterSelected = {
                        onFilterSelected(filter)
                        selectedItem = index
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun FilterOptionItem(
    filter: Filters,
    isSelected: Boolean,
    onFilterSelected: (Filters) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.paddings.small)
            .clickable { onFilterSelected(filter) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = filter.text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        if (isSelected) {
            Icon(
                painter = painterResource(id = com.example.common_ui.R.drawable.ic_select_16),
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}