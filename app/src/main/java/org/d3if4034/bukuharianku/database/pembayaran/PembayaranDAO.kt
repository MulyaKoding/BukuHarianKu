package org.d3if4034.bukuharianku.database.pembayaran

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface PembayaranDAO {

    @Insert
    fun insert(pembayaran: Pembayaran)

    @Update
    fun update(pembayaran: Pembayaran)

    @Query("SELECT * FROM pembayaran")
    fun getPembayaran(): LiveData<List<Pembayaran>>

    @Query("DELETE FROM pembayaran")
    fun clear()

    @Query("DELETE FROM pembayaran WHERE id = :transaksiId")
    fun delete(transaksiId: Long)
}