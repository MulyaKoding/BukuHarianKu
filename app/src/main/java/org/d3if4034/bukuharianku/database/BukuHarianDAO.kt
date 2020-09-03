package org.d3if4034.bukuharianku.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BukuHarianDAO {

    @Insert
    fun insert(bukuHarian: BukuHarian)

    @Update
    fun update(bukuHarian: BukuHarian)

    @Query("SELECT * FROM bukuHarian")
    fun getBukuHarian(): LiveData<List<BukuHarian>>

    @Query("DELETE FROM bukuHarian")
    fun clear()

    @Query("DELETE FROM bukuHarian WHERE id = :bukuHarianId")
    fun delete(bukuHarianId: Long)

}