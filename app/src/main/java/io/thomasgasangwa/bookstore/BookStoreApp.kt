package io.thomasgasangwa.bookstore

import android.app.Application

import io.thomasgasangwa.bookstore.di.bookModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BookStoreApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
           androidContext(this@BookStoreApp)
            modules(bookModule)
        }

    }
}
