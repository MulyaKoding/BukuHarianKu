package org.d3if4034.bukuharianku.utils

import android.view.View
import org.d3if4034.bukuharianku.database.BukuHarian
import org.d3if4034.bukuharianku.database.pembayaran.Pembayaran


interface RecyclerViewClickListener {
    fun onItemClicked(view: View, bukuHarian: BukuHarian)
//    fun onRecyclerViewItemBarangClicked(view: View, barang: Barang)
}