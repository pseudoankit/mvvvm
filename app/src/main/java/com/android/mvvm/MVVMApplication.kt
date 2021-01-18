package com.android.mvvm

import android.app.Application
import com.android.mvvm.data.db.AppDatabase
import com.android.mvvm.data.network.MyApi
import com.android.mvvm.data.network.NetworkConnectionInterceptor
import com.android.mvvm.data.repositories.UserRepository
import com.android.mvvm.ui.auth.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MVVMApplication : Application(),KodeinAware{

    override val kodein = Kodein.lazy{
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepository(instance(),instance()) }
    }
}