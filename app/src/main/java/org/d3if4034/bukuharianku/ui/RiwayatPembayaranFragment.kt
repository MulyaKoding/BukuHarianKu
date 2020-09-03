package org.d3if4034.bukuharianku.ui

import android.app.AlertDialog
import android.content.Intent
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
import org.d3if4034.bukuharianku.databinding.FragmentRiwayatPembayaranBinding
import org.d3if4034.bukuharianku.utils.convertLongToDateString
import org.d3if4034.bukuharianku.utils.rupiah
import org.d3if4034.bukuharianku.viewmodel.pembayaran.PembayaranViewModel

/**
 * A simple [Fragment] subclass.
 */

class RiwayatPembayaranFragment : Fragment() {
    private lateinit var binding: FragmentRiwayatPembayaranBinding
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
    private var tanggal = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_riwayat_pembayaran, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PembayaranDatabase.getInstance(application).pembayaranDAO
        val viewModelFactory = PembayaranViewModel.Factory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PembayaranViewModel::class.java)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share, menu)
        inflater.inflate(R.menu.delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Struk pembelanjaan CashierFromHome untuk $nama"
                )
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Detail Pemesanan:\n" +
                            "Beras $beras kg,\n" +
                            "Gula Pasir $gula kg,\n" +
                            "Garam Beryodium $garam kg,\n" +
                            "Minyak Goreng $minyak liter,\n" +
                            "Daging Sapi $sapi kg,\n" +
                            "Daging Ayam $ayam kg,\n" +
                            "Telur $telur kg,\n" +
                            "Susu Bubuk $susu kg,\n" +
                            "Jagung $jagung kg,\n" +
                            "Mie Instan $mie buah.\n\n" +
                            "Detail Pembayaran:\n" +
                            "Total Harga: " + rupiah(harga) + "\n" +
                            "Total Bayar: " + rupiah(bayar) + "\n" +
                            "Kembalian: " + rupiah(kembalian) + "\n" +
                            "Tanggal Pelunasan: " + convertLongToDateString(tanggal) + "\n\n" +
                            "Terima kasih sudah berbelanja di CashierFromHome, ditunggu 'kedatangan' selanjutnya, ya :')\n" +
                            "Mudah-mudahan wabah COVID-19 ini segera berlalu, Aamiin..."
                )
                shareIntent.putExtra(Intent.EXTRA_EMAIL, "cashierfromhome@gmail.com")
                startActivity(Intent.createChooser(shareIntent, "Bagikan struk ini via..."))
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
            data = arguments?.getParcelable("dataPembayaran")!!
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
            bayar = data.bayar
            kembalian = data.kembalian
            tanggal = data.tanggal

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
            binding.tvBayar.text = rupiah(bayar)
            binding.tvKembalian.text = rupiah(kembalian)
            binding.tvTanggal.text = convertLongToDateString(tanggal)
        }
    }

    private fun showDialogToDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Peringatan!")
        builder.setMessage("Apakah Anda yakin ingin menghapus struk ini?")
        builder.setPositiveButton("Ya") { dialog, _ ->
            viewModel.onClickDelete(data.id)
            dialog.dismiss()
            requireView().findNavController().popBackStack()
            Snackbar.make(
                requireView(),
                "Struk berhasil dihapus!",
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
