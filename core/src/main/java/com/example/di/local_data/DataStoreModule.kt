package com.example.di.local_data

import android.app.Application
import com.example.core.data.settings.SettingsDataStoreImpl
import com.example.core.domain.settings.SettingsDataStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module()
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDb(app: Application) : SettingsDataStore = SettingsDataStoreImpl(app)

}