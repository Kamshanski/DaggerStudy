package edu.kamshanski.daggerstudy

import android.app.Application
import android.content.Context
import edu.kamshanski.daggerstudy.di.AppComponent
import edu.kamshanski.daggerstudy.di.DaggerAppComponent
import kotlin.properties.Delegates

class App: Application() {

    var appComponent: AppComponent by Delegates.notNull()

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)
    }
}

val Context.app: App get() =
    if (this is App)
        this
    else
        applicationContext.app