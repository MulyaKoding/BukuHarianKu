package org.d3if4034.bukuharianku.database.pembayaran

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "pembayaran")
data class Pembayaran(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "nama")
    var nama: String,

    @ColumnInfo(name = "beras")
    var beras: Double,

    @ColumnInfo(name = "gula")
    var gula: Double,

    @ColumnInfo(name = "garam")
    var garam: Double,

    @ColumnInfo(name = "minyak")
    var minyak: Double,

    @ColumnInfo(name = "sapi")
    var sapi: Double,

    @ColumnInfo(name = "ayam")
    var ayam: Double,

    @ColumnInfo(name = "telur")
    var telur: Double,

    @ColumnInfo(name = "susu")
    var susu: Double,

    @ColumnInfo(name = "jagung")
    var jagung: Double,

    @ColumnInfo(name = "mie")
    var mie: Double,

    @ColumnInfo(name = "harga")
    var harga: Double,

    @ColumnInfo(name = "bayar")
    var bayar: Double,

    @ColumnInfo(name = "kembalian")
    var kembalian: Double,

    @ColumnInfo(name = "lunas")
    var lunas: Boolean,

    @ColumnInfo(name = "tanggal")
    var tanggal: Long = System.currentTimeMillis()
) : Parcelable