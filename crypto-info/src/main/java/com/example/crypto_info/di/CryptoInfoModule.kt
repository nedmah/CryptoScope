package com.example.crypto_info.di

import androidx.lifecycle.SavedStateHandle
import com.example.crypto_info.data.remote.CryptoInfoApi
import com.example.crypto_info.domain.repository.CryptoInfoRepository
import com.example.crypto_info.domain.use_case.GetCryptoInfoChartUseCase
import com.example.crypto_info.domain.use_case.GetCryptoInfoUseCase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [CryptoInfoBinding::class])
object CryptoInfoModule {

    @Provides
    @Singleton
    fun provideCryptoInfoApi(retrofit: Retrofit): CryptoInfoApi {
        return retrofit.create(CryptoInfoApi::class.java)
    }

    @Provides
    fun provideCryptoInfoChartUseCase(repository: CryptoInfoRepository) : GetCryptoInfoChartUseCase {
        return GetCryptoInfoChartUseCase(repository)
    }

    @Provides
    fun provideCryptoInfoUseCase(repository: CryptoInfoRepository) : GetCryptoInfoUseCase {
        return GetCryptoInfoUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveStateHandle() : SavedStateHandle{
        return SavedStateHandle()
    }
}