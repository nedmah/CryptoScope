package com.example.di.network

import android.app.Application
import com.example.core.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClientProvider(app: Application): OkHttpClientProvider {
        return OkHttpClientProvider(app)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClientProvider: OkHttpClientProvider) : Retrofit =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClientProvider.provideOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
//            .create()
}