package com.prianshuprasad.myapplication.utils.Database.bookmarkDatabase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import org.junit.runner.manipulation.Ordering
import  android.content.Context
//import notedao
//import notes


@Database(entities = arrayOf(BookmarkData::class),version = 1,exportSchema = false   )
public abstract class BookmarkDatadatabase : RoomDatabase(){

    abstract fun notesDao(): BookmarkDataDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: BookmarkDatadatabase? = null

        fun getDatabase(context: Context): BookmarkDatadatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                     context.applicationContext,
                    BookmarkDatadatabase::class.java,
                    "bookmark_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }




}