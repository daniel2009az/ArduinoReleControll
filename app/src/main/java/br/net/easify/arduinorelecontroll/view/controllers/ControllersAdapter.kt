package br.net.easify.arduinorelecontroll.view.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.net.easify.arduinorelecontroll.R
import br.net.easify.arduinorelecontroll.model.DeviceRelay

class ControllersAdapter(private var onSwitchRelay: OnSwitchRelay)
    : RecyclerView.Adapter<ControllersAdapter.DeviceRelayHolder>() {

    interface OnSwitchRelay {
        fun onSwitchRelay(relay: DeviceRelay)
        fun onEditController(relay: DeviceRelay)
    }

    private var items: ArrayList<DeviceRelay> = ArrayList()
    private var isDeviceConnected = false;
    fun setDeviceConnected(status: Boolean) {
        isDeviceConnected = status
        notifyDataSetChanged()
    }


    class DeviceRelayHolder(itemView: View, onSwitchRelayListener: OnSwitchRelay) :
        RecyclerView.ViewHolder(itemView) {

        lateinit var relay: DeviceRelay

        var deviceName: TextView = itemView.findViewById(R.id.deviceName)
        var switchButton: ImageButton = itemView.findViewById(R.id.switchButton)
        var editController: TextView = itemView.findViewById(R.id.editController)
        var progressIndicator: ProgressBar = itemView.findViewById(R.id.progressIndicator)


        init {
            switchButton.setOnClickListener {
                onSwitchRelayListener.onSwitchRelay(relay)
            }

            editController.setOnClickListener {
                onSwitchRelayListener.onEditController(relay)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceRelayHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holder_device_relay, parent, false)
        return ControllersAdapter.DeviceRelayHolder(
            view, onSwitchRelay
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DeviceRelayHolder, position: Int) {
        holder.relay = items[position]

        val name: String = holder.relay.name!!
        val status: Boolean = holder.relay.status!!
        val connected: Boolean = holder.relay.connected!!

        holder.deviceName.text = name
        if ( connected ) {
            if (status) {
                holder.switchButton.setImageResource(R.drawable.uncheck)
            } else {
                holder.switchButton.setImageResource(R.drawable.check)
            }

            if ( isDeviceConnected ) {
                holder.switchButton.visibility = View.VISIBLE
                holder.progressIndicator.visibility = View.GONE
            } else {
                holder.switchButton.visibility = View.GONE
                holder.progressIndicator.visibility = View.VISIBLE
            }
        } else {
            holder.switchButton.visibility = View.VISIBLE
            holder.progressIndicator.visibility = View.GONE
            holder.switchButton.setImageResource(R.drawable.negative)
        }
    }

    fun updateRelayStatus(relay: DeviceRelay, status: Boolean) {
        val position = items.indexOfFirst { it.id == relay.id }
        if ( position > -1 ) {
            items[position].status = status
            notifyItemChanged(position)
        }
    }

    fun updateData(items: ArrayList<DeviceRelay>) {
        this.items = items
        notifyDataSetChanged()
    }
}