package br.net.easify.arduinorelecontroll.viewmodel.updatecontroller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.arduinorelecontroll.database.AppDatabase
import br.net.easify.arduinorelecontroll.di.component.DaggerDatabaseComponent
import br.net.easify.arduinorelecontroll.di.module.AppModule
import br.net.easify.arduinorelecontroll.model.DeviceRelay
import javax.inject.Inject

class UpdateControllerViewModel(application: Application, deviceRelay: DeviceRelay) :
    AndroidViewModel(application) {

    val relay by lazy { MutableLiveData<DeviceRelay>() }

    @Inject
    lateinit var database: AppDatabase

    init {
        DaggerDatabaseComponent.builder()
            .appModule(AppModule(application))
            .build()
            .inject(this)

        relay.value = deviceRelay
    }

    fun update(): Boolean {
        relay.value.let {
            var savedRelay = database.relayDao().getRelayByid(it!!.id)
            savedRelay!!.name = it.name!!
            savedRelay!!.commandOn = it.commandOn!!
            savedRelay!!.commandOff = it.commandOff!!
            savedRelay!!.connected = it.connected!!
            database.relayDao().update(savedRelay)

            return true
        }

        return false
    }
}
