package org.d3if4034.bukuharianku.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import org.d3if4034.bukuharianku.database.BukuHarian
import org.d3if4034.bukuharianku.database.BukuHarianDAO

class BukuHarianViewModel(
    val database: BukuHarianDAO,
    application: Application
) : AndroidViewModel(application) {

    val bukuHarian = database.getBukuHarian()
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onClickInsert(message: String) {
        uiScope.launch {
            val bukuHarian = BukuHarian(0, message)
            insert(bukuHarian)
        }
    }

    private suspend fun insert(bukuHarian: BukuHarian) {
        withContext(Dispatchers.IO) {
            database.insert(bukuHarian)
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

    fun onClickUpdate(bukuHarian: BukuHarian) {
        uiScope.launch {
            update(bukuHarian)
        }
    }

    private suspend fun update(bukuHarian: BukuHarian) {
        withContext(Dispatchers.IO) {
            database.update(bukuHarian)
        }
    }

    fun onClickDelete(BukuHarianId: Long) {
        uiScope.launch {
            delete(BukuHarianId)
        }
    }

    private suspend fun delete(bukuHarianId: Long) {
        withContext(Dispatchers.IO) {
            database.delete(bukuHarianId)
        }
    }

    class Factory(
        private val dataSource: BukuHarianDAO,
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BukuHarianViewModel::class.java)) {
                return BukuHarianViewModel(
                    dataSource,
                    application
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}