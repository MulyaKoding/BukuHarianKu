package org.d3if4034.bukuharianku

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import org.d3if4034.bukuharianku.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.home = this
        binding.btnTambahpengalaman.setOnClickListener {
            view?.findNavController()
                ?.navigate(HomeFragmentDirections.actionHomeFragmentToMainPengalamanFragment())
        }

        binding.btnDaftarbelanjaharian.setOnClickListener {
            view?.findNavController()
                ?.navigate(HomeFragmentDirections.actionHomeFragmentToDaftarBelanjaHarianFragment())
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aboutFragment -> {
                findNavController()
                    .navigate(R.id.action_homeFragment_to_aboutFragment2)
                true
            }

            R.id.dark_mode -> {

                if (isDarkModeOn()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    startActivity(Intent(requireContext(), MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    })
                    Snackbar.make(
                        requireView(),
                        "Dark mode berhasil dimatikan! Urutan kembali seperti biasa.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    startActivity(Intent(requireContext(), MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    })
                    Snackbar.make(
                        requireView(),
                        "Dark mode berhasil dinyalakan! Urutan kembali seperti biasa.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                true

            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    private fun isDarkModeOn(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }





}
