package br.net.easify.arduinorelecontroll.di.component

import br.net.easify.arduinorelecontroll.di.module.AppModule
import br.net.easify.arduinorelecontroll.di.module.DatabaseModule
import br.net.easify.arduinorelecontroll.viewmodel.controllers.ControllersViewModel
import br.net.easify.arduinorelecontroll.viewmodel.main.MainViewModel
import br.net.easify.arduinorelecontroll.viewmodel.splashscreen.SplashScreenViewModel
import br.net.easify.arduinorelecontroll.viewmodel.updatecontroller.UpdateControllerViewModel
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