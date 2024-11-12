package com.example.my_coins.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.common_ui.theme.paddings

@Composable
fun MyCoinsScreen(
    modifier: Modifier = Modifier,
){

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = modifier.padding(top = MaterialTheme.paddings.xxLarge),
            text = "Кошелёк",
            style = MaterialTheme.typography.headlineSmall
        )

    }

}

@Composable
@PreviewLightDark
fun PreviewWallet(

) {
    MyCoinsScreen()
}