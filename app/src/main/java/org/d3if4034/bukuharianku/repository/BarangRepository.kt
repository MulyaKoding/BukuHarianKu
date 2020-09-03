package org.d3if4034.bukuharianku.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.d3if4034.bukuharianku.database.barang.BarangDatabase
import org.d3if4034.bukuharianku.database.barang.convertToDatabaseModel
import org.d3if4034.bukuharianku.database.barang.convertToDomainModel
import org.d3if4034.bukuharianku.model.Barang
import org.d3if4034.bukuharianku.network.BarangAPI

class BarangRepository(private val database: BarangDatabase) {

    val barang: LiveData<List<Barang>> = Transformations.map(database.barangDAO.getBarang()) {
        convertToDomainModel(it)
    }

    suspend fun refreshBarang() {
        withContext(Dispatchers.IO) {
            val barang = BarangAPI.retrofitService.showList()
            database.barangDAO.insertAll(convertToDatabaseModel(barang))
        }
    }

}