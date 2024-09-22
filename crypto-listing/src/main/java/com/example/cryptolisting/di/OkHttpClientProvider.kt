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

        // API Key Interceptor
        val apiKeyInterceptor = { chain: okhttp3.Interceptor.Chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("X-API-KEY", BuildConfig.API_KEY)
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
}