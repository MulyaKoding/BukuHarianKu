package org.d3if4034.bukuharianku.database.barang

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.d3if4034.bukuharianku.model.Barang


@Entity(tableName = "barang")
data class BarangDatabaseModel constructor(
    @PrimaryKey
    var barang: String,
    var harga: Int,
    var satuan: String,
    var gambar: String = ""
)

fun convertToDomainModel(data: List<BarangDatabaseModel>): List<Barang> {
    return data.map {
        Barang(
            barang = it.barang,
            harga = it.harga,
            satuan = it.satuan,
            gambar = it.gambar
        )
    }
}

fun convertToDatabaseModel(data: List<Barang>): List<BarangDatabaseModel> {
    return data.map {
        BarangDatabaseModel(
            barang = it.barang,
            harga = it.harga,
            satuan = it.satuan,
            gambar = it.gambar
        )
    }
}