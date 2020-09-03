package org.d3if4034.bukuharianku.viewmodel.pembayaran

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.d3if4034.bukuharianku.R
import org.d3if4034.bukuharianku.database.pembayaran.Pembayaran
import org.d3if4034.bukuharianku.databinding.RecyclerviewPembayaranBinding
import org.d3if4034.bukuharianku.utils.RecyclerViewClickListener
import org.d3if4034.bukuharianku.utils.RecyclerViewPembayaranClickListener
import org.d3if4034.bukuharianku.utils.convertLongToDateString
import org.d3if4034.bukuharianku.utils.rupiah


class PembayaranAdapter(private val pembayaran: List<Pembayaran>) :
    RecyclerView.Adapter<PembayaranAdapter.TransaksiViewHolder>() {

    var listener: RecyclerViewPembayaranClickListener? = null

    inner class TransaksiViewHolder(
        val recyclerviewPembayaranBinding: RecyclerviewPembayaranBinding
    ) : RecyclerView.ViewHolder(recyclerviewPembayaranBinding.root)

    override fun getItemCount() = pembayaran.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TransaksiViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recyclerview_pembayaran, parent, false
        )
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        holder.recyclerviewPembayaranBinding.tvTanggal.text =
            convertLongToDateString(pembayaran[position].tanggal)
        if (pembayaran[position].lunas) {
            holder.recyclerviewPembayaranBinding.tvNama.text = pembayaran[position].nama + " (Lunas)"
        } else {
            holder.recyclerviewPembayaranBinding.tvNama.text =
                pembayaran[position].nama + " (Belum Lunas)"
        }
        holder.recyclerviewPembayaranBinding.tvHarga.text = rupiah(pembayaran[position].harga)
        holder.recyclerviewPembayaranBinding.listTransaksi.setOnClickListener {
            listener?.onRecyclerViewItemTransaksiClicked(it, pembayaran[position])
        }
    }
}