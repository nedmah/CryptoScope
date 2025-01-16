package com.example.cryptoscope.di

import android.app.Application
import android.content.Context
import com.example.crypto_info.di.CryptoInfoModule
import com.example.cryptolisting.di.ListingsModule
import com.example.cryptoscope.MainActivity
import com.example.cryptoscope.di.viewmodel.AppBindsModule
import com.example.cryptoscope.di.viewmodel.MultiViewModelFactory
import com.example.cryptoscope.di.viewmodel.ViewModelFactoryModule
import com.example.di.db.DataStoreModule
import com.example.di.db.DatabaseModule
import com.example.di.network.NetworkModule
import com.example.news.di.NewsModule
import com.example.wallet.di.WalletModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


class CryptoScopeApplication : Application() {

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

@Singleton
@Component(
    modules = [
        AppBindsModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        ListingsModule::class,
        CryptoInfoModule::class,
        WalletModule::class,
        NewsModule::class,
        ViewModelFactoryModule::class,
        DataStoreModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    val factory: MultiViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is CryptoScopeApplication -> appComponent
        else -> this.applicationContext.appComponent
    }