package org.d3if4034.bukuharianku.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import org.d3if4034.bukuharianku.R
import org.d3if4034.bukuharianku.databinding.FragmentDaftarBelanjaHarianBinding

/**
 * A simple [Fragment] subclass.
 */
class DaftarBelanjaHarianFragment : Fragment() {
    private lateinit var binding: FragmentDaftarBelanjaHarianBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_daftar_belanja_harian, container, false)

        binding.apply {
            btnTambah.setOnClickListener {
                findNavController().navigate(R.id.action_daftarBelanjaHarianFragment_to_tambahPembayaranFragment)
            }
            btnLihatDaftar.setOnClickListener {
                findNavController().navigate(R.id.action_daftarBelanjaHarianFragment_to_listPembayaranFragment)
            }
            btnLihatBarang.setOnClickListener {
                findNavController().navigate(R.id.action_daftarBelanjaHarianFragment_to_listBarangFragment)
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.dark_mode -> {
//                if (isDarkModeOn()) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    startActivity(Intent(requireContext(), MainActivity::class.java).apply {
//                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    })
//                    Snackbar.make(
//                        requireView(),
//                        "Dark mode berhasil dimatikan!,
//                        Snackbar.LENGTH_SHORT
//                    ).show()
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    startActivity(Intent(requireContext(), MainActivity::class.java).apply {
//                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    })
//                    Snackbar.make(
//                        requireView(),
//                        "Dark mode berhasil dinyalakan!",
//                        Snackbar.LENGTH_SHORT
//                    ).show()
//                }
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    private fun isDarkModeOn(): Boolean {
//        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
//        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
//    }
}
