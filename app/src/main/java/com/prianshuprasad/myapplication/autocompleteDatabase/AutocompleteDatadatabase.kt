package com.prianshuprasad.myapplication.autocompleteDatabase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import org.junit.runner.manipulation.Ordering
import  android.content.Context
//import notedao
//import notes


@Database(entities = arrayOf(AutoCompleteData::class),version = 1,exportSchema = false   )
public abstract class AutocompleteDatadatabase : RoomDatabase(){

    abstract fun notesDao(): AutocompleteDataDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AutocompleteDatadatabase? = null

        fun getDatabase(context: Context): AutocompleteDatadatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                     context.applicationContext,
                    AutocompleteDatadatabase::class.java,
                    "autocomplete_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }




}