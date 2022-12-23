package com.prianshuprasad.myapplication.siteDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

//import notes

@Dao
interface SiteDataDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(history: SiteData)
    @Delete
    suspend fun delete(history: SiteData)

    @Query("Select * from site_table order by coreAdress DESC")
     fun getAll(): LiveData<List<SiteData>>

    @Query("DELETE FROM site_table where coreAdress != 'Settings' ")
    fun deleteAll()


}