package com.prianshuprasad.myapplication.savedTabDatabase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import org.junit.runner.manipulation.Ordering
import  android.content.Context
//import notedao
//import notes


@Database(entities = arrayOf(savedTab::class),version = 1,exportSchema = false   )
public abstract class SavedTabdatabase : RoomDatabase(){

    abstract fun notesDao(): SavedTabDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SavedTabdatabase? = null

        fun getDatabase(context: Context): SavedTabdatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SavedTabdatabase::class.java,
                    "tab_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }




}