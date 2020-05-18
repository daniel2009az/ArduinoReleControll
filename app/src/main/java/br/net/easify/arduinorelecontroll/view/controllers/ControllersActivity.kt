package br.net.easify.arduinorelecontroll.view.controllers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import br.net.easify.arduinorelecontroll.R
import br.net.easify.arduinorelecontroll.model.DeviceRelay
import br.net.easify.arduinorelecontroll.utils.CustomAlertDialog
import br.net.easify.arduinorelecontroll.view.main.MainActivity
import br.net.easify.arduinorelecontroll.view.updatecontroller.UpdateControllerActivity
import br.net.easify.arduinorelecontroll.viewmodel.controllers.ControllersViewModel
import br.net.easify.arduinorelecontroll.viewmodel.controllers.ControllersViewModelFactory
import kotlinx.android.synthetic.main.activity_controllers.*

class ControllersActivity : AppCompatActivity(), ControllersAdapter.OnSwitchRelay {
    private val editControll = 100

    private lateinit var viewModel: ControllersViewModel
    private lateinit var deviceAddress: String
    private lateinit var adapter: ControllersAdapter

    private var alerta: AlertDialog? = null

    private val controllersObserver = Observer { controllers: ArrayList<DeviceRelay> ->
        controllers.let {
            this.adapter.updateData(it)
        }
    }

    private val bluetoothConnectionObserver = Observer { isConnected: Boolean  ->
        isConnected.let {
            adapter.setDeviceConnected(true)
            if ( !it ) {
                alerta = CustomAlertDialog.show(this@ControllersActivity, getString(R.string.warning_message),
                    getString(R.string.not_connected),
                    getString(R.string.exit_button), View.OnClickListener {
                        alerta!!.dismiss()
                        finish()
                    }
                )
            } else {
                layoutProgress.visibility = View.GONE
            }
        }
    }

    companion object {
        val relayExtra: String = "relayExtra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controllers)

        deviceAddress = intent.getStringExtra(MainActivity.extraDeviceAddress)

        deviceName.text = "Conectando a " + deviceAddress

        viewModel = ViewModelProviders.of(
            this,
            ControllersViewModelFactory(
                application,
                deviceAddress
            )
        ).get(ControllersViewModel::class.java)

        adapter = ControllersAdapter(this)
        deviceRelays.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this, 2)
        deviceRelays.layoutManager = gridLayoutManager

        viewModel.controllers.observe(this, controllersObserver)
        viewModel.bluetoothConnected.observe(this, bluetoothConnectionObserver)

        viewModel.getControllers()
    }

    override fun onResume() {
        super.onResume()
        adapter.setDeviceConnected(false)
        layoutProgress.visibility = View.VISIBLE
        viewModel.connectDevice()
    }

    override fun onPause() {
        super.onPause()
        viewModel.disconnectDevice()
    }

    override fun onStop() {
        super.onStop()
        viewModel.disconnectDevice()
    }

    override fun onSwitchRelay(relay: DeviceRelay) {
        if ( relay.connected ) {
            val status = !relay.status
            if (status) {
                viewModel.sendCommand(relay.commandOn!!)
            } else {
                viewModel.sendCommand(relay.commandOff!!)
            }
            adapter.updateRelayStatus(relay, status)
        }
    }

    override fun onEditController(relay: DeviceRelay) {
        var intent: Intent = Intent(this, UpdateControllerActivity::class.java)
        intent.putExtra(relayExtra, relay)
        startActivityForResult(intent, editControll)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ( requestCode == editControll && resultCode == Activity.RESULT_OK) {
            viewModel.getControllers()
        }
    }
}
