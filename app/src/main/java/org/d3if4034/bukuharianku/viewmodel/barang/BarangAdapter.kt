package org.d3if4034.bukuharianku.viewmodel.barang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if4034.bukuharianku.R
import org.d3if4034.bukuharianku.databinding.RecyclerviewBarangBinding
import org.d3if4034.bukuharianku.model.Barang
import org.d3if4034.bukuharianku.utils.rupiah

class BarangAdapter(
    private val barang: List<Barang>
) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    inner class BarangViewHolder(
        val recyclerviewBarangBinding: RecyclerviewBarangBinding
    ) : RecyclerView.ViewHolder(recyclerviewBarangBinding.root)

    override fun getItemCount() = barang.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BarangViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recyclerview_barang, parent, false
        )
    )

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        holder.recyclerviewBarangBinding.tvJudulBarang.text = barang[position].barang
        holder.recyclerviewBarangBinding.tvHargaBarang.text =
            rupiah(barang[position].harga.toDouble()) + barang[position].satuan

        if (barang[position].gambar == "") {
            Glide.with(holder.itemView.context).clear(holder.recyclerviewBarangBinding.image)
            holder.recyclerviewBarangBinding.image.setImageDrawable(null)
            holder.recyclerviewBarangBinding.image.visibility = View.GONE
        } else {
            Glide.with(holder.itemView.context)
                .load(barang[position].gambar)
                .placeholder(R.drawable.ic_launcher_foreground)
                .dontAnimate()
                .into(holder.recyclerviewBarangBinding.image)
        }
    }
}