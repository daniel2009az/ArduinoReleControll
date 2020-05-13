package br.net.easify.arduinorelecontroll.view.main

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.net.easify.arduinorelecontroll.R

class DeviceAdapter(private var onDeviceSelectedListener: OnDeviceSelected): RecyclerView.Adapter<DeviceAdapter.DeviceHolder>() {

    interface OnDeviceSelected {
        fun onDeviceSelected(device: BluetoothDevice)
    }

    private var items: ArrayList<BluetoothDevice> = ArrayList()

    class DeviceHolder(itemView: View, onDeviceSelectedListener: OnDeviceSelected) :
        RecyclerView.ViewHolder(itemView) {

        var deviceName: TextView = itemView.findViewById(R.id.deviceName)
        var deviceAddress: TextView = itemView.findViewById(R.id.deviceAddress)

        lateinit var device: BluetoothDevice

        init {
            itemView.setOnClickListener {
                onDeviceSelectedListener.onDeviceSelected(device)
            }
        }
    }

    fun updateData(items: ArrayList<BluetoothDevice>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_bluetooth_device, parent, false)
        return DeviceHolder(
            view,
            onDeviceSelectedListener
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DeviceHolder, position: Int) {
        holder.device = items[position]

        val name: String = holder.device.name!!
        val address: String = holder.device.address!!

        holder.deviceName.text = name
        holder.deviceAddress.text = address
    }
}