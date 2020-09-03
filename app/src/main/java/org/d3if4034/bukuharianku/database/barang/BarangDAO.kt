package org.d3if4034.bukuharianku.database.barang

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BarangDAO {

    @Query("SELECT * FROM barang")
    fun getBarang(): LiveData<List<BarangDatabaseModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(miwok: List<BarangDatabaseModel>)
}