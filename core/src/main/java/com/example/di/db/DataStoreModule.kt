package com.example.di.db

import android.app.Application
import androidx.room.Room
import com.example.core.data.db.CryptoDb
import com.example.core.data.db.daos.CryptoListingsDao
import com.example.core.data.settings.SettingsDataStoreImpl
import com.example.core.domain.settings.SettingsDataStore
import com.example.di.CoreBinding
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module()
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDb(app: Application) : SettingsDataStore = SettingsDataStoreImpl(app)

}