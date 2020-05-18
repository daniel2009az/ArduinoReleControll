package br.net.easify.arduinorelecontroll.viewmodel.controllers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.arduinorelecontroll.database.AppDatabase
import br.net.easify.arduinorelecontroll.di.component.DaggerDatabaseComponent
import br.net.easify.arduinorelecontroll.di.module.AppModule
import br.net.easify.arduinorelecontroll.model.DeviceRelay
import br.net.easify.arduinorelecontroll.services.BluetoothService
import javax.inject.Inject
import kotlin.collections.ArrayList

class ControllersViewModel(application: Application, private var deviceAddress: String) :
    AndroidViewModel(application), BluetoothService.ConnectionStatus {

    val controllers by lazy { MutableLiveData<ArrayList<DeviceRelay>>() }
    val bluetoothConnected by lazy { MutableLiveData<Boolean>() }
    private var isBluetoothConnected = false

    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var bluetoothService: BluetoothService

    init {
        DaggerDatabaseComponent.builder()
            .appModule(AppModule(application))
            .build()
            .inject(this)
    }

    fun connectDevice() {
        if (!bluetoothService.isConnected()) {
            bluetoothService.connectDevice(this, deviceAddress)
        } else {
            setBluetoothConnected(true)
        }
    }

    fun disconnectDevice() {
        bluetoothService.disconnectDevice()
    }

    fun getControllers() {
        var data = database.relayDao().getAll()
        if (data != null) {
            var temp: ArrayList<DeviceRelay> = ArrayList()
            for (item in data) {
                temp.add(
                    DeviceRelay(
                        item.Id,
                        item.name,
                        item.status,
                        item.connected,
                        item.commandOn,
                        item.commandOff
                    )
                )
            }
            controllers.value = temp
        }
    }

    fun sendCommand(input: String) {
        if ( isBluetoothConnected) {
            bluetoothService.sendCommand(input)
        }
    }

    override fun onConnection(status: Boolean) {
        setBluetoothConnected(status)
    }

    private fun setBluetoothConnected(status: Boolean) {
        isBluetoothConnected = status
        bluetoothConnected.value = isBluetoothConnected
    }
}