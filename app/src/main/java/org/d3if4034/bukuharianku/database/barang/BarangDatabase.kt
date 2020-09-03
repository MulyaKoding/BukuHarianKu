package org.d3if4034.bukuharianku.database.barang

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [BarangDatabaseModel::class], version = 1, exportSchema = false)
abstract class BarangDatabase : RoomDatabase() {

    abstract val barangDAO: BarangDAO

    companion object {
        @Volatile
        private var INSTANCE: BarangDatabase? = null

        fun getInstance(context: Context) : BarangDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BarangDatabase::class.java,
                        "db_barang"
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