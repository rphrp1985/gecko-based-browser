package com.prianshuprasad.myapplication.savedTabDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

//import notes


@Dao
interface SavedTabDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(history: savedTab)
    @Delete
    suspend fun delete(history: savedTab)

    @Query("Select * from tab_table order by id DESC")
     fun getAll(): LiveData<List<savedTab>>

     @Query("DELETE FROM tab_table")
     fun deleteAll()



}