package yb.kompose.recipetoshoppinglist.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import yb.kompose.recipetoshoppinglist.features.cooking.di.mealDBApiModule
import yb.kompose.recipetoshoppinglist.features.core.di.databaseModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                databaseModule,
                mealDBApiModule
            )
        }
    }
}