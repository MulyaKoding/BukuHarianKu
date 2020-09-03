package org.d3if4034.bukuharianku.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.d3if4034.bukuharianku.MainActivity
import org.d3if4034.bukuharianku.R
import org.d3if4034.bukuharianku.database.BukuHarian
import org.d3if4034.bukuharianku.database.BukuHarianDatabase
import org.d3if4034.bukuharianku.databinding.FragmentMainPengalamanBinding
import org.d3if4034.bukuharianku.utils.RecyclerViewClickListener

@Suppress("SpellCheckingInspection")
class MainPengalamanFragment : Fragment(),
    RecyclerViewClickListener {

    private lateinit var binding: FragmentMainPengalamanBinding
    private lateinit var viewModel: BukuHarianViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_main_pengalaman, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = BukuHarianDatabase.getInstance(application).BukuHarianDAO
        val viewModelFactory = BukuHarianViewModel.Factory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BukuHarianViewModel::class.java)

        // set lifecycle
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init UI
        initUI()

        // fab tulis diary action
        binding.fabTulisDiary.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainPengalamanFragment_to_tambahPengalamanFragment)
        }

    }

    private fun initUI() {
        viewModel.bukuHarian.observe(viewLifecycleOwner, Observer {
            val bukuHarianAdapter = BukuHarianAdapter(it)
            val recyclerView = binding.rvBukuharian

            recyclerView.apply {
                this.adapter = bukuHarianAdapter
                this.layoutManager = LinearLayoutManager(requireContext())

            }

            // set click listener
            bukuHarianAdapter.listener = this
        })
    }


    override fun onItemClicked(view: View, bukuHarian: BukuHarian) {
        when (view.id) {
            R.id.list_bukuharian -> {
                val bundle = bundleOf("dataDiary" to bukuHarian)
                view.findNavController().navigate(R.id.action_mainPengalamanFragment_to_editPengalamanFragment, bundle)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu1, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.hapus_bukuharian -> {
                showDialogToDelete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialogToDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Peringatan!")
        builder.setMessage("Yakin ingin menghapus semua cerita?")
        builder.setPositiveButton("Ya") { dialog, _ ->
            viewModel.onClickClear()
            dialog.dismiss()
            Snackbar.make(requireView(), getString(R.string.success_remove), Snackbar.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.show()
    }
}
