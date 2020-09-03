package org.d3if4034.bukuharianku.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar

import org.d3if4034.bukuharianku.R
import org.d3if4034.bukuharianku.database.pembayaran.PembayaranDatabase
import org.d3if4034.bukuharianku.databinding.FragmentTambahPembayaranBinding
import org.d3if4034.bukuharianku.utils.rupiah
import org.d3if4034.bukuharianku.viewmodel.pembayaran.PembayaranViewModel

/**
 * A simple [Fragment] subclass.
 */

class TambahPembayaranFragment : Fragment() {
    private lateinit var binding: FragmentTambahPembayaranBinding
    private lateinit var viewModel: PembayaranViewModel
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tambah_pembayaran, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PembayaranDatabase.getInstance(application).pembayaranDAO
        val viewModelFactory = PembayaranViewModel.Factory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PembayaranViewModel::class.java)

        binding.apply {
            btnHitung.setOnClickListener {
                if (!(binding.etNama.text.toString()
                        .isEmpty() || binding.etBeras.text.toString()
                        .isEmpty() || binding.etGula.text.toString()
                        .isEmpty() || binding.etGaram.text.toString()
                        .isEmpty() || binding.etMinyak.text.toString()
                        .isEmpty() || binding.etSapi.text.toString()
                        .isEmpty() || binding.etAyam.text.toString()
                        .isEmpty() || binding.etTelur.text.toString()
                        .isEmpty() || binding.etSusu.text.toString()
                        .isEmpty() || binding.etJagung.text.toString()
                        .isEmpty() || binding.etMie.text.toString()
                        .isEmpty())
                ) {
                    nama = etNama.text.toString()
                    beras = etBeras.text.toString().toDouble()
                    gula = etGula.text.toString().toDouble()
                    garam = etGaram.text.toString().toDouble()
                    minyak = etMinyak.text.toString().toDouble()
                    sapi = etSapi.text.toString().toDouble()
                    ayam = etAyam.text.toString().toDouble()
                    telur = etTelur.text.toString().toDouble()
                    susu = etSusu.text.toString().toDouble()
                    jagung = etJagung.text.toString().toDouble()
                    mie = etMie.text.toString().toDouble()
                    hitungHarga()
                } else {
                    Snackbar.make(
                        requireView(),
                        "Mohon diisi dengan lengkap!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    binding.tvJudulHarga.visibility = View.GONE
                    binding.tvHarga.visibility = View.GONE
                }
            }
            btnReset.setOnClickListener {
                reset()
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun hitungHarga() {
        harga =
            beras * 10800 + gula * 9000 + garam * 7200 + minyak * 13500 + sapi * 94500 + ayam * 63000 + telur * 16200 + susu * 32400 + jagung * 8100 + mie * 2700
        binding.tvJudulHarga.visibility = View.VISIBLE
        binding.tvHarga.visibility = View.VISIBLE
        binding.tvHarga.text = rupiah(harga)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.commit -> {
                if (!(binding.etNama.text.toString()
                        .isEmpty() || binding.etBeras.text.toString()
                        .isEmpty() || binding.etGula.text.toString()
                        .isEmpty() || binding.etGaram.text.toString()
                        .isEmpty() || binding.etMinyak.text.toString()
                        .isEmpty() || binding.etSapi.text.toString()
                        .isEmpty() || binding.etAyam.text.toString()
                        .isEmpty() || binding.etTelur.text.toString()
                        .isEmpty() || binding.etSusu.text.toString()
                        .isEmpty() || binding.etJagung.text.toString()
                        .isEmpty() || binding.etMie.text.toString()
                        .isEmpty())
                ) {
                    insertData()
                    requireView().findNavController().popBackStack()
                    Snackbar.make(
                        requireView(),
                        "Pemesanan berhasil!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(
                        requireView(),
                        "Mohon diisi dengan lengkap!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun insertData() {
        nama = binding.etNama.text.toString()
        beras = binding.etBeras.text.toString().toDouble()
        gula = binding.etGula.text.toString().toDouble()
        garam = binding.etGaram.text.toString().toDouble()
        minyak = binding.etMinyak.text.toString().toDouble()
        sapi = binding.etSapi.text.toString().toDouble()
        ayam = binding.etAyam.text.toString().toDouble()
        telur = binding.etTelur.text.toString().toDouble()
        susu = binding.etSusu.text.toString().toDouble()
        jagung = binding.etJagung.text.toString().toDouble()
        mie = binding.etMie.text.toString().toDouble()

        viewModel.onClickInsert(
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
            harga
        )
    }

    private fun reset() {
        binding.etNama.text.clear()
        binding.etBeras.text.clear()
        binding.etGula.text.clear()
        binding.etGaram.text.clear()
        binding.etMinyak.text.clear()
        binding.etSapi.text.clear()
        binding.etAyam.text.clear()
        binding.etTelur.text.clear()
        binding.etSusu.text.clear()
        binding.etJagung.text.clear()
        binding.etMie.text.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.commit, menu)
    }
}
