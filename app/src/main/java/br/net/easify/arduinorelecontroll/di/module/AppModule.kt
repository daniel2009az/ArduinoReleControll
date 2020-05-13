package br.net.easify.arduinorelecontroll.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var application: Application) {

    @Provides
    fun providesApplication(): Application {
        return application
    }
}