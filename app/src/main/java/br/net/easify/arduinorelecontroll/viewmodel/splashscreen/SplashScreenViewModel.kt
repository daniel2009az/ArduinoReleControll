package br.net.easify.arduinorelecontroll.viewmodel.splashscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.net.easify.arduinorelecontroll.database.AppDatabase
import br.net.easify.arduinorelecontroll.database.model.Relay
import br.net.easify.arduinorelecontroll.di.component.DaggerDatabaseComponent
import br.net.easify.arduinorelecontroll.di.module.AppModule
import javax.inject.Inject

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var database: AppDatabase

    init {
        DaggerDatabaseComponent.builder()
            .appModule(AppModule(application))
            .build()
            .inject(this)
    }

    fun updateDatabase() {
        var data = database.relayDao().getAll()
        if (data.isNullOrEmpty() || data?.size == 0) {
            for (i: Int in 1..8) {
                database.relayDao().insert(
                    Relay(
                        i,
                        "Relé $i",
                        false,
                        false,
                        (65 + i - 1).toChar().toString(),
                        (97 + i - 1).toChar().toString()
                    )
                )
            }
        }
    }
}