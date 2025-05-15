package io.thomasgasangwa.bookstore

import android.app.Application

import io.thomasgasangwa.bookstore.di.localBookModule
import io.thomasgasangwa.bookstore.di.remoteBookModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BookStoreApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
           androidContext(this@BookStoreApp)
            modules(localBookModule, remoteBookModule)
        }

    }
}
