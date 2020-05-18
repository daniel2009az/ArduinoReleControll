package br.net.easify.arduinorelecontroll.view.main

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.net.easify.arduinorelecontroll.R
import br.net.easify.arduinorelecontroll.utils.CustomAlertDialog
import br.net.easify.arduinorelecontroll.view.controllers.ControllersActivity
import br.net.easify.arduinorelecontroll.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DeviceAdapter.OnDeviceSelected {
    private val requestEnabledBluetooth = 101

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: DeviceAdapter

    private var alerta: AlertDialog? = null

    companion object {
        val extraDeviceAddress: String = "DeviceAddress"
    }

    private val bluetoothAdapterObserver = Observer { adapter: BluetoothAdapter ->
        adapter.let {
            if (!it.isEnabled) {
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBluetoothIntent, requestEnabledBluetooth)
            } else {
                viewModel.getPairedDeviceList(it)
            }
        }
    }

    private val pairedDevicesObserver = Observer { devices: ArrayList<BluetoothDevice> ->
        devices.let { items: ArrayList<BluetoothDevice> ->
            this.adapter.updateData(items)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.bluetoothAdapter.observe(this, bluetoothAdapterObserver)
        viewModel.pairedDevices.observe(this, pairedDevicesObserver)

        adapter = DeviceAdapter(this)
        bluetoothDevices.adapter = adapter
        val llm = LinearLayoutManager(this)
        llm.orientation = RecyclerView.VERTICAL
        bluetoothDevices.layoutManager = llm
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestEnabledBluetooth) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.getBluetoothAdapters()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                alerta = CustomAlertDialog.show(this@MainActivity, getString(R.string.warning_message),
                    getString(R.string.bluetooth_is_disabled),
                    getString(R.string.exit_button), View.OnClickListener {
                        alerta!!.dismiss()
                        finish()
                    }
                )
            }
        }
    }

    override fun onDeviceSelected(device: BluetoothDevice) {
        var intent = Intent(this, ControllersActivity::class.java)
        intent.putExtra(extraDeviceAddress, device.address)
        startActivity(intent)
    }
}
