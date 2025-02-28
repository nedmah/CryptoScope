package com.example.di.local_data

import android.app.Application
import androidx.room.Room
import com.example.core.data.db.CryptoDb
import com.example.core.data.db.daos.CryptoListingsDao
import com.example.di.CoreBinding
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [CoreBinding::class])
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(app: Application) : CryptoDb {
        return Room.databaseBuilder(app, CryptoDb::class.java,"cryptoDb.db").build()
    }

    @Provides
    @Singleton
    fun provideListingsDao(db: CryptoDb) : CryptoListingsDao = db.getCryptoListingsDao()


}