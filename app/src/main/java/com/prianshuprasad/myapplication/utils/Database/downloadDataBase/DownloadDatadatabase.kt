package com.prianshuprasad.myapplication.utils.Database.downloadDataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import org.junit.runner.manipulation.Ordering
import  android.content.Context
//import notedao
//import notes


@Database(entities = arrayOf(DownloadData::class),version = 1,exportSchema = false   )
public abstract class DownloadDatadatabase : RoomDatabase(){

    abstract fun notesDao(): DownloadDataDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DownloadDatadatabase? = null

        fun getDatabase(context: Context): DownloadDatadatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                     context.applicationContext,
                    DownloadDatadatabase::class.java,
                    "download_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }




}