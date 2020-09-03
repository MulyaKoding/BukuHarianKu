package org.d3if4034.bukuharianku.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.d3if4034.bukuharianku.database.barang.BarangDatabase
import org.d3if4034.bukuharianku.repository.BarangRepository
import retrofit2.HttpException


class RefreshDataWork(
    appContext: Context, params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = BarangDatabase.getInstance(applicationContext)
        val repository = BarangRepository(database)

        return try {
            repository.refreshBarang()
            Result.success()
        } catch (execption: HttpException) {
            Result.retry()
        }
    }
}