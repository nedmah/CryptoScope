package com.example.cryptolisting.di

import android.app.Application
import com.example.core.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Singleton

@Singleton
class OkHttpClientProvider (private val app : Application) {

    fun provideOkHttpClient(): OkHttpClient {
        // Размер кэша (5 MB)
        val cacheSize = 5 * 1024 * 1024 // 5 MB
        val cacheDir = File(app.cacheDir, "http_cache")
        val cache = Cache(cacheDir, cacheSize.toLong())

        // Настройка HttpLoggingInterceptor для логирования запросов и ответов
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Настройка OkHttpClient с кэшем и логированием
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(logging)
            .addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                //время жизни кэша 300 секунд
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=300")
                    .header("X-API-KEY", BuildConfig.API_KEY)
                    .build()
            }
            .build()
    }
}