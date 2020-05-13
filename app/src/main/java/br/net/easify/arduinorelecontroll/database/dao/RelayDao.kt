package br.net.easify.arduinorelecontroll.database.dao

import androidx.room.*
import br.net.easify.arduinorelecontroll.database.model.Relay

@Dao
interface RelayDao {

    @Query("SELECT * FROM relay WHERE id = :id")
    fun getRelayByid(id: Int?): Relay?

    @Query("SELECT * FROM relay ORDER BY id")
    fun getAll(): List<Relay>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(visits: List<Relay>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(visit: Relay?)

    @Query("DELETE FROM Relay WHERE id = :id")
    fun delete(id: Int?)

    @Update
    fun update(visit: Relay?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(visit: List<Relay>?)
}