package com.example.crypto_info.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.common_ui.composable.FavoriteIcon
import com.example.common_ui.theme.paddings

@Composable
fun CryptoInfoHeader(
    name: String,
    isFavourite: Boolean,
    onFavouritePushed: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    isFavouriteEnabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.paddings.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onBack()
            },
            painter = painterResource(id = com.example.common_ui.R.drawable.ic_back_24),
            contentDescription = null
        )

        Box(
            modifier = modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = modifier,
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (isFavouriteEnabled) FavoriteIcon(isFavourite = isFavourite, onValueChanged = onFavouritePushed)
    }
}