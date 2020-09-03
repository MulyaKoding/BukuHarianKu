package org.d3if4034.bukuharianku.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "bukuHarian")
data class BukuHarian(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,

    @ColumnInfo(name = "message")
    var message:String,

    @ColumnInfo(name = "tanggal")
    val tanggal:Long = System.currentTimeMillis()
) : Parcelable