package org.d3if4034.bukuharianku.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar

import org.d3if4034.bukuharianku.R
import org.d3if4034.bukuharianku.database.pembayaran.Pembayaran
import org.d3if4034.bukuharianku.database.pembayaran.PembayaranDatabase
import org.d3if4034.bukuharianku.databinding.FragmentEditPembayaranBinding
import org.d3if4034.bukuharianku.utils.rupiah
import org.d3if4034.bukuharianku.viewmodel.pembayaran.PembayaranViewModel

/**
 * A simple [Fragment] subclass.
 */
class EditPembayaranFragment : Fragment() {
    private lateinit var binding: FragmentEditPembayaranBinding
    private lateinit var viewModel: PembayaranViewModel
    private lateinit var data: Pembayaran
    private var nama = ""
    private var beras = 0.00
    private var gula = 0.00
    private var garam = 0.00
    private var minyak = 0.00
    private var sapi = 0.00
    private var ayam = 0.00
    private var telur = 0.00
    private var susu = 0.00
    private var jagung = 0.00
    private var mie = 0.00
    private var harga = 0.00
    private var bayar = 0.00
    private var kembalian = 0.00
    private var lunas = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_pembayaran, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PembayaranDatabase.getInstance(application).pembayaranDAO
        val viewModelFactory = PembayaranViewModel.Factory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PembayaranViewModel::class.java)

        binding.apply {
            btnHitung.setOnClickListener {
                if (binding.etBayar.text.toString().isNotEmpty()) {
                    bayar = etBayar.text.toString().toDouble()
                    kembalian = bayar - harga
                    tvJudulKembalian.visibility = View.VISIBLE
                    tvKembalian.visibility = View.VISIBLE

                    if (kembalian >= 0) {
                        tvKembalian.text = rupiah(kembalian)
                        lunas = true
                    } else {
                        tvKembalian.text = "Maaf, uang Anda kurang mencukupi!"
                        lunas = false
                    }
                } else {
                    Snackbar.make(
                        requireView(),
                        "Mohon uangnya diisi!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.commit, menu)
        inflater.inflate(R.menu.delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.commit -> {
                kembalian = bayar - harga
                this.lunas = kembalian >= 0

                if (lunas) {
                    updateData()
                    requireView().findNavController().popBackStack()
                    Snackbar.make(
                        requireView(),
                        "Pembelian berhasil!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(
                        requireView(),
                        "Maaf, uang Anda kurang mencukupi!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                return true
            }

            R.id.delete -> {
                showDialogToDelete()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getData() {
        if (arguments != null) {
            data = arguments?.getParcelable("dataTransaksi")!!
            nama = data.nama
            beras = data.beras
            gula = data.gula
            garam = data.garam
            minyak = data.minyak
            sapi = data.sapi
            ayam = data.ayam
            telur = data.telur
            susu = data.susu
            jagung = data.jagung
            mie = data.mie
            harga = data.harga

            binding.tvNama.text = "Nama Pembeli: " + nama
            binding.tvBeras.text = beras.toString()
            binding.tvGula.text = gula.toString()
            binding.tvGaram.text = garam.toString()
            binding.tvMinyak.text = minyak.toString()
            binding.tvSapi.text = sapi.toString()
            binding.tvAyam.text = ayam.toString()
            binding.tvTelur.text = telur.toString()
            binding.tvSusu.text = susu.toString()
            binding.tvJagung.text = jagung.toString()
            binding.tvMie.text = mie.toString()
            binding.tvHarga.text = rupiah(harga)
        }
    }

    private fun updateData() {
        val pembayaran = Pembayaran(
            data.id,
            nama,
            beras,
            gula,
            garam,
            minyak,
            sapi,
            ayam,
            telur,
            susu,
            jagung,
            mie,
            harga, bayar, kembalian, lunas, System.currentTimeMillis()
        )
        viewModel.onClickUpdate(pembayaran)
    }

    private fun showDialogToDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Peringatan!")
        builder.setMessage("Apakah Anda yakin ingin membatalkan pemesanan ini?")
        builder.setPositiveButton("Ya") { dialog, _ ->
            viewModel.onClickDelete(data.id)
            dialog.dismiss()
            requireView().findNavController().popBackStack()
            Snackbar.make(
                requireView(),
                "Pemesanan berhasil dibatalkan!",
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
