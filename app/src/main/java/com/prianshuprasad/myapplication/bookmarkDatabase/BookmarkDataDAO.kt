package com.prianshuprasad.myapplication.bookmarkDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE

//import notes

@Dao
interface BookmarkDataDAO {

    @Insert(onConflict = IGNORE)
    suspend fun insert(history: BookmarkData)
    @Delete
    suspend fun delete(history: BookmarkData)

    @Query("Select * from bookmark_table ")
     fun getAll(): LiveData<List<BookmarkData>>

    @Query("DELETE FROM bookmark_table ")
    fun deleteAll()


}