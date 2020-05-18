package br.net.easify.arduinorelecontroll.viewmodel.updatecontroller

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.net.easify.arduinorelecontroll.model.DeviceRelay

class UpdateControllerViewModelFactory(private val application: Application,
                                       private val deviceRelay: DeviceRelay) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpdateControllerViewModel(
            application,
            deviceRelay
        ) as T
    }
}