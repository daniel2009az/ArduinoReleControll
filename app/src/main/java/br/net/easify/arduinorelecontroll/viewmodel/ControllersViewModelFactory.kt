package br.net.easify.arduinorelecontroll.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ControllersViewModelFactory(private val application: Application,
                                  private val deviceAddress: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ControllersViewModel(application, deviceAddress) as T
    }
}