package com.prianshuprasad.myapplication.autocompleteDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE

//import notes

@Dao
interface AutocompleteDataDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(history: AutoCompleteData)
    @Delete
    suspend fun delete(history: AutoCompleteData)

    @Query("Select * from autocomplete_table ")
     fun getAll(): LiveData<List<AutoCompleteData>>

    @Query("DELETE FROM autocomplete_table ")
    fun deleteAll()


}