package com.prianshuprasad.myapplication.utils.Database.offlinePagesDataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

//import notes


@Dao
interface OfflinePageDAO {

    @Insert
    suspend fun insert(history: offlinePage)
    @Delete
    suspend fun delete(history: offlinePage)

    @Query("Select * from offline_table order by id DESC")
     fun getAll(): LiveData<List<offlinePage>>

     @Query("DELETE FROM offline_table")
     fun deleteAll()



}