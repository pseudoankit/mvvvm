package com.android.mvvm

import android.app.Application
import com.android.mvvm.data.db.AppDatabase
import com.android.mvvm.data.db.preferences.PreferenceProvider
import com.android.mvvm.data.network.MyApi
import com.android.mvvm.data.network.NetworkConnectionInterceptor
import com.android.mvvm.data.repositories.QuotesRepository
import com.android.mvvm.data.repositories.UserRepository
import com.android.mvvm.ui.auth.AuthViewModelFactory
import com.android.mvvm.ui.home.profile.ProfileViewModelFactory
import com.android.mvvm.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(),instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }
    }
}