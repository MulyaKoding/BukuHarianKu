package org.d3if4034.bukuharianku.database.pembayaran

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pembayaran::class], version = 1, exportSchema = false)
abstract class PembayaranDatabase : RoomDatabase() {

    abstract val pembayaranDAO: PembayaranDAO

    companion object {
        @Volatile
        private var INSTANCE: PembayaranDatabase? = null

        fun getInstance(context: Context) : PembayaranDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PembayaranDatabase::class.java,
                        "db_transaksi"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}