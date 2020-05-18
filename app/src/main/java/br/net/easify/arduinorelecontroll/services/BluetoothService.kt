package br.net.easify.arduinorelecontroll.services

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.AsyncTask
import java.io.IOException
import java.util.*

class BluetoothService {
    interface ConnectionStatus {
        fun onConnection(status: Boolean)
    }

    private val uuId: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private lateinit var bluetoothSocket: BluetoothSocket

    private var isConnected: Boolean = false

    fun getDefaultAdapter(): BluetoothAdapter {
        return bluetoothAdapter
    }

    fun sendCommand(input: String) {
        if (isConnected) {
            try {
                bluetoothSocket.outputStream.write(input.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun isConnected(): Boolean = isConnected

    fun connectDevice(delegate: ConnectionStatus, deviceAddress: String) {
        ConnectToDevice(delegate, deviceAddress).execute()
    }

    fun disconnectDevice() {
        try {
            if (isConnected) {
                bluetoothSocket.close()
                isConnected = false
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    inner class ConnectToDevice(private val delegate: ConnectionStatus,
                                private val deviceAddress: String)
        : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg p0: Void?): Boolean {
            try {
                if (!isConnected) {
                    val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress)
                    bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(uuId)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    bluetoothSocket.connect()
                    return true
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return false
        }

        override fun onPostExecute(result: Boolean) {
            super.onPostExecute(result)
            isConnected = result
            delegate.onConnection(isConnected)
        }
    }
}