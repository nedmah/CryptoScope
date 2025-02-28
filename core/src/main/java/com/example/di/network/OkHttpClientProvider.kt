package com.example.di.network

import android.app.Application
import android.content.SharedPreferences
import com.example.core.BuildConfig
import com.example.core.data.settings.SettingsConstants
import com.example.core.util.isValidApiKey
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Singleton

@Singleton
class OkHttpClientProvider (
    private val app : Application,
    private val sharedPreferences: SharedPreferences
) {

    fun provideOkHttpClient(): OkHttpClient {
        val cacheSize = 5 * 1024 * 1024 // 5 MB
        val cacheDir = File(app.cacheDir, "http_cache")
        val cache = Cache(cacheDir, cacheSize.toLong())


        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // API Key Interceptor
        val apiKeyInterceptor = { chain: okhttp3.Interceptor.Chain ->

            val original = chain.request()
            val api = getApiKeyFromPreferences() ?: BuildConfig.API_KEY
            val requestBuilder = original.newBuilder()
                .header("X-API-KEY", api)
                .header("accept", "application/json")

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        // Настройка OkHttpClient с кэшем и логированием
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(logging)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    private fun getApiKeyFromPreferences(): String? {
        val key = sharedPreferences.getString(SettingsConstants.API_KEY, null)
        return if (key != null && isValidApiKey(key)) key else null
    }
}