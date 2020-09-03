package org.d3if4034.bukuharianku.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Barang(
    var barang: String,
    var harga: Int,
    var satuan: String,
    var gambar: String = ""
)