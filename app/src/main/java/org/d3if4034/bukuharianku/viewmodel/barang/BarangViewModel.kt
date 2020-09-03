package org.d3if4034.bukuharianku.viewmodel.barang

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.d3if4034.bukuharianku.database.barang.BarangDatabase
import org.d3if4034.bukuharianku.model.Barang
import org.d3if4034.bukuharianku.network.BarangAPI
import org.d3if4034.bukuharianku.network.BarangService
import org.d3if4034.bukuharianku.repository.BarangRepository


class BarangViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _barang = MutableLiveData<List<Barang>>()
    val barang: LiveData<List<Barang>>
        get() = _barang


    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    init {
        _loading.value = true
        uiScope.launch {
            try {
                val result = BarangAPI.retrofitService.showList()
                _barang.value = result
            } catch (t: Throwable) {
                _response.value = "Tidak ada koneksi internet!"
            } finally {
                _loading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}

