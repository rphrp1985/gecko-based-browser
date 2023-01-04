package com.prianshuprasad.myapplication.utils.Database.downloadDataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

//import notes

@Dao
interface DownloadDataDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(history: DownloadData)
    @Delete
    suspend fun delete(history: DownloadData)

    @Query("Select * from download_table order by did DESC ")
     fun getAll(): LiveData<List<DownloadData>>

    @Query("DELETE FROM download_table ")
    fun deleteAll()


}