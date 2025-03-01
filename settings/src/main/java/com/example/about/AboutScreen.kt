package com.example.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.common_ui.theme.paddings
import com.example.core.util.openCustomTab


@Composable
fun AboutScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
){

    val context = LocalContext.current
    val versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "Unknown"
    val mail = "9hamden@gmail.com"
    val github = "https://github.com/nedmah"

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
            text = stringResource(id = com.example.common_ui.R.string.info),
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = stringResource(id = com.example.common_ui.R.string.version, versionName),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = MaterialTheme.paddings.medium)
        )


        Text(
            text = stringResource(id = com.example.common_ui.R.string.email, mail),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = MaterialTheme.paddings.medium).clickable {
                val emailIntent = android.content.Intent(android.content.Intent.ACTION_SENDTO).apply {
                    data = android.net.Uri.parse("mailto:$mail")
                }
                emailIntent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(emailIntent)
            }
        )


        Text(
            text = stringResource(id = com.example.common_ui.R.string.github, github),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = MaterialTheme.paddings.medium).clickable {
                openCustomTab(context,github)
            }
        )


    }
}