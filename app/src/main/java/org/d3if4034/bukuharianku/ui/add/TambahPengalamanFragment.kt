package org.d3if4034.bukuharianku.ui.add

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar

import org.d3if4034.bukuharianku.R
import org.d3if4034.bukuharianku.database.BukuHarianDatabase
import org.d3if4034.bukuharianku.databinding.FragmentTambahPengalamanBinding
import org.d3if4034.bukuharianku.ui.home.BukuHarianViewModel

/**
 * A simple [Fragment] subclass.
 */

@Suppress("SpellCheckingInspection")
class TambahPengalamanFragment : Fragment() {

    private lateinit var binding: FragmentTambahPengalamanBinding
    private lateinit var viewModel: BukuHarianViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // show action bar
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_tambah_pengalaman, container, false)

        // set viewmodel
        val application = requireNotNull(this.activity).application
        val dataSource = BukuHarianDatabase.getInstance(application).BukuHarianDAO
        val viewModelFactory = BukuHarianViewModel.Factory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BukuHarianViewModel::class.java)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_button, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.item_action -> {
                if (inputCheck()) {
                    // navigate ke fragment sebelumnya
                    requireView().findNavController().popBackStack()
                    Snackbar.make(requireView(), getString(R.string.success_insert), Snackbar.LENGTH_SHORT).show()

                } else {
                    Snackbar.make(requireView(), getString(R.string.null_message), Snackbar.LENGTH_SHORT).show()
                }
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun inputCheck(): Boolean {
        return when {
            binding.etBukuharian.text.trim().isEmpty() -> false
            else -> {
                viewModel.onClickInsert(binding.etBukuharian.text.toString())
                true
            }

        }
    }

}

