package com.example.cryptoscope.di

import android.app.Application
import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.crypto_info.di.CryptoInfoModule
import com.example.cryptolisting.di.ListingsModule
import com.example.cryptoscope.MainActivity
import com.example.cryptoscope.di.viewmodel.AppBindsModule
import com.example.cryptoscope.di.viewmodel.MultiViewModelFactory
import com.example.cryptoscope.di.viewmodel.ViewModelFactoryModule
import com.example.cryptoscope.worker.CurrencyUpdateWorker
import com.example.di.SettingsModule
import com.example.di.db.DataStoreModule
import com.example.di.db.DatabaseModule
import com.example.di.network.NetworkModule
import com.example.news.di.NewsModule
import com.example.wallet.di.WalletModule
import dagger.BindsInstance
import dagger.Component
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


class CryptoScopeApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)

        setupWorkManager()
    }


    private fun setupWorkManager() {
        val workRequest = PeriodicWorkRequestBuilder<CurrencyUpdateWorker>(24, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "CurrencyUpdateWork",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
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
        DataStoreModule::class,
        SettingsModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(app: CryptoScopeApplication)

    fun inject(worker: CurrencyUpdateWorker)

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