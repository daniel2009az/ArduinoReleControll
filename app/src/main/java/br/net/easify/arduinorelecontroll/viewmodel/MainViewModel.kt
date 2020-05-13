package br.net.easify.arduinorelecontroll.viewmodel

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.arduinorelecontroll.database.AppDatabase
import br.net.easify.arduinorelecontroll.di.component.DaggerDatabaseComponent
import br.net.easify.arduinorelecontroll.di.module.AppModule
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val bluetoothAdapter by lazy { MutableLiveData<BluetoothAdapter>() }
    val pairedDevices by lazy { MutableLiveData<ArrayList<BluetoothDevice>>() }

    @Inject
    lateinit var database: AppDatabase

    init {
        DaggerDatabaseComponent.builder()
            .appModule(AppModule(application))
            .build()
            .inject(this)

        getBluetoothAdapters()
    }

    fun getBluetoothAdapters() {
        bluetoothAdapter.value = BluetoothAdapter.getDefaultAdapter()
    }

    fun getPairedDeviceList(adapter: BluetoothAdapter) {
        var pairedDevices = adapter.bondedDevices
        val list: ArrayList<BluetoothDevice> = ArrayList()
        if (pairedDevices.isNotEmpty()) {
            for (device: BluetoothDevice in pairedDevices) {
                list.add(device)
            }
        }

        this.pairedDevices.value = list
    }
}