package com.example.di.db

import android.app.Application
import androidx.room.Room
import com.example.core.db.CryptoDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(app: Application) : CryptoDb {
        return Room.databaseBuilder(app, CryptoDb::class.java,"cryptoDb.db").build()
    }

}