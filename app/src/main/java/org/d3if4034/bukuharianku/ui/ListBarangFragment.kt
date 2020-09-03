package org.d3if4034.bukuharianku.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar

import org.d3if4034.bukuharianku.R
import org.d3if4034.bukuharianku.databinding.FragmentListBarangBinding
import org.d3if4034.bukuharianku.viewmodel.barang.BarangAdapter
import org.d3if4034.bukuharianku.viewmodel.barang.BarangViewModel

/**
 * A simple [Fragment] subclass.
 */

class ListBarangFragment : Fragment() {
    private lateinit var binding: FragmentListBarangBinding
    private lateinit var viewModel: BarangViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_barang, container, false)
        viewModel = ViewModelProvider(this).get(BarangViewModel::class.java)

        binding.barangViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.barang.observe(viewLifecycleOwner, Observer {
            Log.i("testing", it.toString())
            val barangAdapter = BarangAdapter(it)
            val recyclerView = binding.rvBarang

            recyclerView.apply {
                this.adapter = barangAdapter
                this.layoutManager = LinearLayoutManager(requireContext())
            }
        })

        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}