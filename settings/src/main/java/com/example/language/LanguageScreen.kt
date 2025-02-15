package com.example.language

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.common_ui.theme.extraColor
import com.example.common_ui.theme.paddings
import com.example.core.util.LocaleHelper
import com.example.settings.R

@Composable
fun LanguageScreen(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    onBack: () -> Unit,
    onLanguageSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LanguageViewModel = viewModel(factory = getViewModelFactory())
) {

    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val context = LocalContext.current
    val availableLanguages = listOf(
        LanguageItem(
            com.example.common_ui.R.string.locale_russian,
            com.example.common_ui.R.string.russian,
            com.example.common_ui.R.drawable.ic_flag_ru
        ),
        LanguageItem(
            com.example.common_ui.R.string.locale_english,
            com.example.common_ui.R.string.english,
            com.example.common_ui.R.drawable.ic_flag_en
        ),
        LanguageItem(
            com.example.common_ui.R.string.locale_kazakh,
            com.example.common_ui.R.string.kazakh,
            com.example.common_ui.R.drawable.ic_flag_kz
        ),
    )


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = modifier
                .align(Alignment.Start)
                .padding(top = MaterialTheme.paddings.extraLarge)
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
            text = stringResource(id = com.example.common_ui.R.string.select_language),
            style = MaterialTheme.typography.headlineSmall
        )

        LazyColumn {
            items(availableLanguages.size) { index ->
                val language = availableLanguages[index]
                val localeCode = stringResource(id = language.locale)

                ListItem(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable {
                            LocaleHelper.updateLocale(context, localeCode)
                            viewModel.changeLanguage(localeCode)
                            onLanguageSelected(localeCode)
                        }
                        .padding(MaterialTheme.paddings.small),
                    headlineContent = { Text(text = stringResource(id = language.description)) },
                    leadingContent = {
                        Image(
                            modifier = modifier,
                            painter = painterResource(id = language.imageId),
                            contentDescription = null
                        )
                    },
                    trailingContent = {
                        AnimatedVisibility(visible = stringResource(id = language.locale) == selectedLanguage) {
                            Icon(
                                modifier = modifier.weight(1f),
                                painter = painterResource(id = com.example.common_ui.R.drawable.ic_select_16),
                                tint = MaterialTheme.extraColor.chart,
                                contentDescription = null
                            )
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    }
}