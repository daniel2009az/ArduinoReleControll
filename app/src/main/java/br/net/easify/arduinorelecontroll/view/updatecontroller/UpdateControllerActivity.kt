package br.net.easify.arduinorelecontroll.view.updatecontroller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.net.easify.arduinorelecontroll.R
import br.net.easify.arduinorelecontroll.databinding.ActivityUpdateControllerBinding
import br.net.easify.arduinorelecontroll.model.DeviceRelay
import br.net.easify.arduinorelecontroll.view.controllers.ControllersActivity
import br.net.easify.arduinorelecontroll.viewmodel.updatecontroller.UpdateControllerViewModel
import br.net.easify.arduinorelecontroll.viewmodel.updatecontroller.UpdateControllerViewModelFactory

class UpdateControllerActivity : AppCompatActivity() {

    private lateinit var viewModel: UpdateControllerViewModel
    private lateinit var deviceRelay: DeviceRelay
    private lateinit var dataBinding: ActivityUpdateControllerBinding

    private val relayObserver = Observer { relay: DeviceRelay ->
        relay.let {
            deviceRelay = it
            dataBinding.deviceRelay = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_update_controller)

        deviceRelay = intent.getParcelableExtra(ControllersActivity.relayExtra)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(
            this,
            UpdateControllerViewModelFactory(
                application,
                deviceRelay
            )
        ).get(UpdateControllerViewModel::class.java)

        viewModel.relay.observe(this, relayObserver)

        dataBinding.deviceRelay = deviceRelay
        dataBinding.updateButton.setOnClickListener(View.OnClickListener {
            if (viewModel.update()) {
                var intentResult = Intent()
                setResult(Activity.RESULT_OK, intentResult)
                finish()
            } else {
                Toast.makeText(
                    this@UpdateControllerActivity,
                    "Não foi possível gravar o controle",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
