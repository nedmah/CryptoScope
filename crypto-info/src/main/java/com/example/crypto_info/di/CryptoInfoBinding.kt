package com.example.crypto_info.di

import com.example.crypto_info.data.repository.CryptoInfoRepositoryImpl
import com.example.crypto_info.domain.repository.CryptoInfoRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface CryptoInfoBinding {

    @Binds
    @Singleton
    fun bindCryptoInfoRepository(cryptoInfoRepositoryImpl: CryptoInfoRepositoryImpl) : CryptoInfoRepository

}