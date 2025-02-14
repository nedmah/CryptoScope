package com.example.cryptolisting.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.common_ui.composable.BottomDialog
import com.example.common_ui.theme.paddings
import com.example.common_ui.theme.spacers
import com.example.cryptolisting.R
import com.example.cryptolisting.presentation.Filters

@Composable
fun FilterBottomSheet(
    onFilterSelected: (Filters) -> Unit,
    onDismiss: () -> Unit,
    showDialog: Boolean,
    modifier: Modifier = Modifier
) {
    val filterOptions = Filters.entries.toList()
    var selectedItem by remember { mutableIntStateOf(0) }


    BottomDialog(
        showDialog = showDialog,
        onDismiss = { onDismiss() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = modifier
                    .width(32.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    .padding(
                        top = MaterialTheme.paddings.small
                    )
            )
            Text(style = MaterialTheme.typography.headlineSmall, text = stringResource(id = com.example.common_ui.R.string.sorting), modifier = Modifier.padding(vertical = MaterialTheme.paddings.medium))
            HorizontalDivider()
            filterOptions.forEachIndexed { index, filter ->
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
            Spacer(modifier = modifier.height(MaterialTheme.spacers.large))
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
            text = stringResource(id = filter.text),
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