package org.d3if4034.bukuharianku.ui.edit

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import org.d3if4034.bukuharianku.R
import org.d3if4034.bukuharianku.database.BukuHarian
import org.d3if4034.bukuharianku.database.BukuHarianDatabase
import org.d3if4034.bukuharianku.databinding.FragmentEditPengalamanBinding
import org.d3if4034.bukuharianku.ui.home.BukuHarianViewModel

/**
 * A simple [Fragment] subclass.
 */
@Suppress("SpellCheckingInspection")
class EditPengalamanFragment : Fragment() {

    private lateinit var binding: FragmentEditPengalamanBinding
    private lateinit var viewModel: BukuHarianViewModel
    private lateinit var data: BukuHarian

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_edit_pengalaman, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = BukuHarianDatabase.getInstance(application).BukuHarianDAO
        val viewModelFactory = BukuHarianViewModel.Factory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BukuHarianViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            data = arguments?.getParcelable("dataDiary")!!
            binding.etBukuharianUpdate.setText(data.message)
        }

    }

    // create overflow menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_button, menu)
        inflater.inflate(R.menu.menu1, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.item_action -> {
                if (inputCheck()) {
                    // back stack
                    requireView().findNavController().popBackStack()
                    Snackbar.make(requireView(), getString(R.string.success_update), Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(requireView(), getString(R.string.null_message), Snackbar.LENGTH_SHORT).show()
                }
                true
            }

            R.id.hapus_bukuharian -> {
                showDialogToDelete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun inputCheck(): Boolean {
        return when {
            binding.etBukuharianUpdate.text.trim().isEmpty() -> false
            else -> {
                doUpdate()
                true
            }
        }
    }

    private fun doUpdate() {
        val message = binding.etBukuharianUpdate.text.toString()
        val date = System.currentTimeMillis()

        val bukuHarian = BukuHarian(data.id, message, date)
        viewModel.onClickUpdate(bukuHarian)
    }


    private fun showDialogToDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Peringatan!")
        builder.setMessage("Yakin ingin menghapus cerita?")
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
            viewModel.onClickDelete(data.id)
            requireView().findNavController().popBackStack()
            Snackbar.make(requireView(), getString(R.string.success_remove), Snackbar.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.show()
    }
}
