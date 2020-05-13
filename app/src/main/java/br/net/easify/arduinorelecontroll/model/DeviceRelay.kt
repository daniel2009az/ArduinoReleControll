package br.net.easify.arduinorelecontroll.model

import android.os.Parcel
import android.os.Parcelable

data class DeviceRelay(
    var id: Int,
    var name: String?,
    var status: Boolean,
    var connected: Boolean,
    var commandOn: String?,
    var commandOff: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeByte(if (status) 1 else 0)
        parcel.writeByte(if (connected) 1 else 0)
        parcel.writeString(commandOn)
        parcel.writeString(commandOff)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DeviceRelay> {
        override fun createFromParcel(parcel: Parcel): DeviceRelay {
            return DeviceRelay(parcel)
        }

        override fun newArray(size: Int): Array<DeviceRelay?> {
            return arrayOfNulls(size)
        }
    }
}