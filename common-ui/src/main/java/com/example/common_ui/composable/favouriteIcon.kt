package com.example.common_ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteIcon(
    modifier: Modifier = Modifier,
    isFavourite : Boolean,
    onValueChanged : () -> Unit
) {
    var isFavorite by remember { mutableStateOf(isFavourite) }


    Icon(
        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
        tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isFavorite = !isFavorite
                onValueChanged()
            }
    )
}

@PreviewLightDark
@Composable
fun FavoriteIconPreview() {
    PreviewWrapper {
        FavoriteIcon(isFavourite = false){}
    }
}