package com.prianshuprasad.myapplication.offlinePagesDataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import org.junit.runner.manipulation.Ordering
import  android.content.Context
//import notedao
//import notes


@Database(entities = arrayOf(offlinePage::class),version = 1,exportSchema = false   )
public abstract class OfflibePagedatabase : RoomDatabase(){

    abstract fun notesDao(): OfflinePageDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: OfflibePagedatabase? = null

        fun getDatabase(context: Context): OfflibePagedatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OfflibePagedatabase::class.java,
                    "offline_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }




}