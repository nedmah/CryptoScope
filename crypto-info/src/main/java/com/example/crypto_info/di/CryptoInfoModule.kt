package com.example.crypto_info.di

import com.example.crypto_info.data.remote.CryptoInfoApi
import com.example.crypto_info.domain.repository.CryptoInfoRepository
import com.example.crypto_info.domain.use_case.GetCryptoInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoInfoModule {

    @Provides
    @Singleton
    fun provideCryptoInfoApi(retrofit: Retrofit): CryptoInfoApi {
        return retrofit.create(CryptoInfoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCryptoInfoUseCase(repository: CryptoInfoRepository) : GetCryptoInfoUseCase {
        return GetCryptoInfoUseCase(repository)
    }
}