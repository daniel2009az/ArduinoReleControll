package br.net.easify.arduinorelecontroll.di.component

import br.net.easify.arduinorelecontroll.MainApplication
import br.net.easify.arduinorelecontroll.di.module.AppModule
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent?
        fun application(appModule: AppModule): Builder
    }

    fun inject(app: MainApplication)
}