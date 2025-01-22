package com.example.accounts.presentation

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.example.common_ui.theme.paddings

@Composable
fun AccountsScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    onBack : () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountsViewModel = viewModel(factory = getViewModelFactory())
) {


    var isDialogVisible by remember { mutableStateOf(false) }
    var selectedOption1 by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val state = viewModel.state.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = modifier
                .align(Alignment.Start)
                .padding(top = MaterialTheme.paddings.large)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onBack()
                },
            painter = painterResource(id = com.example.common_ui.R.drawable.ic_back_24),
            contentDescription = null
        )

        Text(
            modifier = modifier.padding(
                top = MaterialTheme.paddings.xxLarge,
                bottom = MaterialTheme.paddings.xxLarge
            ),
            text = "Мои счета",
            style = MaterialTheme.typography.headlineSmall
        )

        LazyColumn {

            items(state.accounts.size){ index ->
                val item = state.accounts[index]

                AccountItem(
                    address = item.address,
                    onClick = { viewModel.onEvent(AccountsEvents.OnAccountSelect(item)) },
                    onDelete = { viewModel.onEvent(AccountsEvents.DeleteAccount(item)) },
                    isSelected = item.isSelected,
                    image = item.imageUrl,
                    blockchain = item.blockChain
                )
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                IconButton(
                    modifier = modifier
                        .background(color = MaterialTheme.colorScheme.background, shape = CircleShape),
                    onClick = {
                        isDialogVisible = true
                        viewModel.onEvent(AccountsEvents.OnAddPush)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = com.example.common_ui.R.drawable.ic_add_24),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        contentDescription = null
                    )
                }
                }
            }
        }

        if (isDialogVisible)
                AccountsDialogBox(
                    options1 = state.blockchains.map { it.name },
                    onDismissRequest = { isDialogVisible = false },
                    onConfirm = {
                        isDialogVisible = false
                        if (selectedOption1.isBlank()) viewModel.onEvent(AccountsEvents.AddAccount(null, address))
                        else viewModel.onEvent(AccountsEvents.AddAccount(selectedOption1, address))
                    },
                    selectedOption = selectedOption1,
                    onOptionSelected = {selectedOption1 = it},
                    address = address,
                    onAddressChange = {address = it}
                )


    }

}