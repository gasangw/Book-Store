package io.thomasgasangwa.bookcollection

import android.app.Application
import android.content.pm.ApplicationInfo
import io.thomasgasangwa.bookcollection.di.localBookModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class BookStoreApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@BookStoreApp)
            modules(localBookModule)
        }

    }
}
