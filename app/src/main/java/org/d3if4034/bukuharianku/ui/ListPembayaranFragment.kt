package org.d3if4034.bukuharianku.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar

import org.d3if4034.bukuharianku.R
import org.d3if4034.bukuharianku.database.pembayaran.Pembayaran
import org.d3if4034.bukuharianku.database.pembayaran.PembayaranDatabase
import org.d3if4034.bukuharianku.databinding.FragmentListPembayaranBinding
import org.d3if4034.bukuharianku.utils.RecyclerViewClickListener
import org.d3if4034.bukuharianku.utils.RecyclerViewPembayaranClickListener
import org.d3if4034.bukuharianku.viewmodel.pembayaran.PembayaranAdapter
import org.d3if4034.bukuharianku.viewmodel.pembayaran.PembayaranViewModel

/**
 * A simple [Fragment] subclass.
 */

class ListPembayaranFragment : Fragment(), RecyclerViewPembayaranClickListener
{
    private lateinit var binding: FragmentListPembayaranBinding
    private lateinit var viewModel: PembayaranViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_pembayaran, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PembayaranDatabase.getInstance(application).pembayaranDAO
        val viewModelFactory = PembayaranViewModel.Factory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PembayaranViewModel::class.java)

        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete, menu)
    }

    override fun onRecyclerViewItemTransaksiClicked(view: View, pembayaran: Pembayaran) {
        when (view.id) {
            R.id.list_transaksi -> {
                val bundle = bundleOf("dataTransaksi" to pembayaran)
                if (pembayaran.lunas) {
                    view.findNavController()
                        .navigate(
                            R.id.action_listPembayaranFragment_to_riwayatPembayaranFragment,
                            bundle
                        )
                } else {
                    view.findNavController()
                        .navigate(
                            R.id.action_listPembayaranFragment_to_editPembayaranFragment,
                            bundle
                        )
                }
            }
        }
    }

    private fun initUI() {
        viewModel.pembayaran.observe(viewLifecycleOwner, Observer {
            val pembayaranAdapter = PembayaranAdapter(it)
            val recyclerView = binding.rvTransaksi

            recyclerView.apply {
                this.adapter = pembayaranAdapter
                this.layoutManager = LinearLayoutManager(requireContext())
            }

            pembayaranAdapter.listener = this
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                showDialogToDeleteAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialogToDeleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Peringatan!")
        builder.setMessage("Apakah Anda ingin menghapus semua riwayat transaksi?")
        builder.setPositiveButton("Ya") { dialog, _ ->
            viewModel.onClickClear()
            dialog.dismiss()
            Snackbar.make(
                requireView(),
                "Semua riwayat transaksi dihapus!",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.show()
    }

}
