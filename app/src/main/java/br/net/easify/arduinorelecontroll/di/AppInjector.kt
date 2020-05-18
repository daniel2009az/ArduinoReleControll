package br.net.easify.arduinorelecontroll.di

import br.net.easify.arduinorelecontroll.MainApplication
import br.net.easify.arduinorelecontroll.di.component.DaggerAppComponent
import br.net.easify.arduinorelecontroll.di.module.AppModule

class AppInjector {
    companion object{
        fun init(mainApplication: MainApplication) {
            DaggerAppComponent.builder()
                .application(AppModule(mainApplication))
                .build()!!
                .inject(mainApplication)
        }
    }
}