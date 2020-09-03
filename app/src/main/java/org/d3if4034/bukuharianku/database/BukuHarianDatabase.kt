package org.d3if4034.bukuharianku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [BukuHarian::class], version = 1, exportSchema = false)
abstract class BukuHarianDatabase : RoomDatabase() {
    abstract val BukuHarianDAO: BukuHarianDAO

    companion object {
        @Volatile
        private var INSTANCE: BukuHarianDatabase? = null

        fun getInstance(context: Context): BukuHarianDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BukuHarianDatabase::class.java,
                        "BukuHarian_database"
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