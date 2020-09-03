package org.d3if4034.bukuharianku.utils

import android.view.View
import org.d3if4034.bukuharianku.database.pembayaran.Pembayaran

interface RecyclerViewPembayaranClickListener {
    fun onRecyclerViewItemTransaksiClicked(view: View, pembayaran: Pembayaran)
}