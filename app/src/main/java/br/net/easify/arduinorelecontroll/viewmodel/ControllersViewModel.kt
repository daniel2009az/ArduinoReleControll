package br.net.easify.arduinorelecontroll.viewmodel

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.arduinorelecontroll.database.AppDatabase
import br.net.easify.arduinorelecontroll.di.component.DaggerDatabaseComponent
import br.net.easify.arduinorelecontroll.di.module.AppModule
import br.net.easify.arduinorelecontroll.model.DeviceRelay
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ControllersViewModel(application: Application, deviceAddress: String) :
    AndroidViewModel(application) {

    private val uuId: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private lateinit var bluetoothSocket: BluetoothSocket
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var isConnected: Boolean = false

    val controllers by lazy { MutableLiveData<ArrayList<DeviceRelay>>() }

    @Inject
    lateinit var database: AppDatabase

    init {
        DaggerDatabaseComponent.builder()
            .appModule(AppModule(application))
            .build()
            .inject(this)

        ConnectToDevice(deviceAddress).execute()
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
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket!!.outputStream.write(input.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    inner class ConnectToDevice(private val deviceAddress: String) : AsyncTask<Void, Void, Void>() {
        private var connectSuccess: Boolean = true

        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                if (!isConnected) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = bluetoothAdapter!!.getRemoteDevice(deviceAddress)
                    bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(uuId)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    bluetoothSocket!!.connect()
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            isConnected = connectSuccess
        }
    }
}