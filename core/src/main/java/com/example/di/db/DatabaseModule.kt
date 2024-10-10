package com.example.di.db

import android.app.Application
import androidx.room.Room
import com.example.core.db.CryptoDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(app: Application) : CryptoDb {
        return Room.databaseBuilder(app, CryptoDb::class.java,"cryptoDb.db").build()
    }

}