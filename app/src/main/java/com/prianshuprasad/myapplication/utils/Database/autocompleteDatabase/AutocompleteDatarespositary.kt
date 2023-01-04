package com.prianshuprasad.myapplication.utils.Database.autocompleteDatabase

import androidx.lifecycle.LiveData

class AutocompleteDatarespositary(private  val daonotes: AutocompleteDataDAO) {

    var allnotes:LiveData<List<AutoCompleteData>> = daonotes.getAll()
    suspend fun insert(history: AutoCompleteData){
        daonotes.insert(history);
      daonotes.getAll()
    }
    suspend fun delete(history: AutoCompleteData){
        daonotes.delete(history)
    }
    suspend fun deleteAll(){
        daonotes.deleteAll()
    }




}