package br.net.easify.arduinorelecontroll.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "relay")
data class Relay(

    @PrimaryKey
    val Id: Int,
    var name: String,
    var status: Boolean,
    var connected: Boolean,
    var commandOn: String,
    var commandOff: String
)