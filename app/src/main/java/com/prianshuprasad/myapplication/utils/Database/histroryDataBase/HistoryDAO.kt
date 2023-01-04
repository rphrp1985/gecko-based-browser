package com.prianshuprasad.myapplication.utils.Database.histroryDataBase

import androidx.lifecycle.LiveData
import androidx.room.*
//import notes

@Dao
interface HistoryDAO {

    @Insert
    suspend fun insert(history: BrowsingHistory)
    @Delete
    suspend fun delete(history: BrowsingHistory)

    @Query("Select * from notes_table order by id DESC")
     fun getAll(): LiveData<List<BrowsingHistory>>

     @Query("DELETE FROM notes_table")
     fun deleteAll()



}