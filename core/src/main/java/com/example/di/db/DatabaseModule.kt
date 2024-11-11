package com.example.di.db

import android.app.Application
import androidx.room.Room
import com.example.core.data.db.CryptoDb
import com.example.di.RepositoryBinding
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RepositoryBinding::class])
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(app: Application) : CryptoDb {
        return Room.databaseBuilder(app, CryptoDb::class.java,"cryptoDb.db").build()
    }

}