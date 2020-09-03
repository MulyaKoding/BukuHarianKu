package org.d3if4034.bukuharianku.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.d3if4034.bukuharianku.R
import org.d3if4034.bukuharianku.database.BukuHarian
import org.d3if4034.bukuharianku.databinding.RecyclerviewBukuharianBinding
import org.d3if4034.bukuharianku.utils.RecyclerViewClickListener
import org.d3if4034.bukuharianku.utils.convertLongToDateString


@Suppress("SpellCheckingInspection")
class BukuHarianAdapter(private val BukuHarian: List<BukuHarian>) : RecyclerView.Adapter<BukuHarianAdapter.BukuHarianViewHolder>() {

    var listener: RecyclerViewClickListener? = null

    inner class BukuHarianViewHolder(
        val recyclerViewBukuHarianBinding: RecyclerviewBukuharianBinding
    ) : RecyclerView.ViewHolder(recyclerViewBukuHarianBinding.root)

    override fun getItemCount() = BukuHarian.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BukuHarianViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recyclerview_bukuharian, parent, false)
    )

    override fun onBindViewHolder(holder: BukuHarianViewHolder, position: Int) {
        holder.recyclerViewBukuHarianBinding.tvDate.text = convertLongToDateString(BukuHarian[position].tanggal)
        holder.recyclerViewBukuHarianBinding.tvMessage.text = BukuHarian[position].message

        holder.recyclerViewBukuHarianBinding.listBukuharian.setOnClickListener {

            listener?.onItemClicked(it, BukuHarian[position])
        }
    }
}