package com.example.core.util

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.core.data.settings.SettingsConstants
import com.example.core.domain.settings.SettingsDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

object LocaleHelper {
    fun updateLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }


    private fun getDeviceLocale(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0].language // Android 7.0+
        } else {
            context.resources.configuration.locale.language // До Android 7.0
        }
    }

    suspend fun initializeLocale(dataStore: SettingsDataStore, context: Context) {
        val currentLanguage = dataStore.getString(SettingsConstants.LANGUAGE)
        val language = if (currentLanguage.isNullOrEmpty()) {
            val deviceLocale = getDeviceLocale(context)
            dataStore.putString(SettingsConstants.LANGUAGE, deviceLocale)
            deviceLocale
        } else {
            currentLanguage
        }

        withContext(Dispatchers.Main.immediate) {
            updateLocale(context, language)
        }
    }
}
