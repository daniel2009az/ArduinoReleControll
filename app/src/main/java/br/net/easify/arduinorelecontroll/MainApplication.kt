package br.net.easify.arduinorelecontroll

import android.app.Application
import br.net.easify.arduinorelecontroll.di.AppInjector

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
}