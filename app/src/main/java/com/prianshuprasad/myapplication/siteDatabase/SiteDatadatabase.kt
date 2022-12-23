package com.prianshuprasad.myapplication.siteDatabase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import org.junit.runner.manipulation.Ordering
import  android.content.Context
//import notedao
//import notes


@Database(entities = arrayOf(SiteData::class),version = 1,exportSchema = false   )
public abstract class SiteDatadatabase : RoomDatabase(){

    abstract fun notesDao(): SiteDataDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SiteDatadatabase? = null

        fun getDatabase(context: Context): SiteDatadatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                     context.applicationContext,
                    SiteDatadatabase::class.java,
                    "site_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }




}