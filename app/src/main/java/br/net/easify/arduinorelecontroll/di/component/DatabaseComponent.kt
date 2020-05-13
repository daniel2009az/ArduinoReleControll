package br.net.easify.arduinorelecontroll.di.component

import br.net.easify.arduinorelecontroll.di.module.AppModule
import br.net.easify.arduinorelecontroll.di.module.DatabaseModule
import br.net.easify.arduinorelecontroll.viewmodel.ControllersViewModel
import br.net.easify.arduinorelecontroll.viewmodel.MainViewModel
import br.net.easify.arduinorelecontroll.viewmodel.SplashScreenViewModel
import br.net.easify.arduinorelecontroll.viewmodel.UpdateControllerViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        AppModule::class
    ]
)
interface DatabaseComponent {

    fun inject(viewModel: ControllersViewModel)
    fun inject(viewModel: MainViewModel)
    fun inject(viewModel: SplashScreenViewModel)
    fun inject(viewModel: UpdateControllerViewModel)
}