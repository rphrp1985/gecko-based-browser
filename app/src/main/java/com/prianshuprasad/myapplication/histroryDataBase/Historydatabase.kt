package com.prianshuprasad.myapplication.histroryDataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import org.junit.runner.manipulation.Ordering
import  android.content.Context
//import notedao
//import notes


@Database(entities = arrayOf(BrowsingHistory::class),version = 1,exportSchema = false   )
public abstract class Historydatabase : RoomDatabase(){

    abstract fun notesDao(): HistoryDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: Historydatabase? = null

        fun getDatabase(context: Context): Historydatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Historydatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }




}