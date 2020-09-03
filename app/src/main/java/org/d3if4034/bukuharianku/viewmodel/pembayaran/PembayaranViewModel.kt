package org.d3if4034.bukuharianku.viewmodel.pembayaran

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import org.d3if4034.bukuharianku.database.pembayaran.Pembayaran
import org.d3if4034.bukuharianku.database.pembayaran.PembayaranDAO

class PembayaranViewModel(
    val database: PembayaranDAO, application: Application
) : AndroidViewModel(application) {

    var pembayaran = database.getPembayaran()
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onClickInsert(
        nama: String,
        beras: Double,
        gula: Double,
        garam: Double,
        minyak: Double,
        sapi: Double,
        ayam: Double,
        telur: Double,
        susu: Double,
        jagung: Double,
        mie: Double,
        harga: Double
    ) {
        uiScope.launch {
            val transaksi = Pembayaran(
                0,
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
                harga,
                0.0,
                0.0,
                false,
                System.currentTimeMillis()
            )
            insert(transaksi)
        }
    }

    private suspend fun insert(pembayaran: Pembayaran) {
        withContext(Dispatchers.IO) {
            database.insert(pembayaran)
        }
    }

    fun onClickClear() {
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun onClickUpdate(pembayaran: Pembayaran) {
        uiScope.launch {
            update(pembayaran)
        }
    }

    private suspend fun update(pembayaran: Pembayaran) {
        withContext(Dispatchers.IO) {
            database.update(pembayaran)
        }
    }

    fun onClickDelete(id: Long) {
        uiScope.launch {
            delete(id)
        }
    }

    private suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            database.delete(id)
        }
    }

    class Factory(
        private val dataSource: PembayaranDAO, private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PembayaranViewModel::class.java)) {
                return PembayaranViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}