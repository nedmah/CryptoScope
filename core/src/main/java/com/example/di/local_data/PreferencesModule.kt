package com.example.di.local_data


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object PreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app : Application): SharedPreferences {
        return app.getSharedPreferences("crypto_prefs", Context.MODE_PRIVATE)
    }

}