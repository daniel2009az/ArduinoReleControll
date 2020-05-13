package br.net.easify.arduinorelecontroll.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.net.easify.arduinorelecontroll.database.dao.RelayDao
import br.net.easify.arduinorelecontroll.database.model.Relay

@Database(
    entities = [
        Relay::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun relayDao(): RelayDao

    companion object {
        private var instance: AppDatabase? = null
        private var databaseName = "arduinoRelayControll.sqlite"

        fun getAppDataBase(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        databaseName)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance!!
        }
    }
}