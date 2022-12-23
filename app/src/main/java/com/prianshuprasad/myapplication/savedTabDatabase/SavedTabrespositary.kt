package com.prianshuprasad.myapplication.savedTabDatabase

import androidx.lifecycle.LiveData

class SavedTabrespositary(private  val daonotes: SavedTabDAO) {

    var allnotes:LiveData<List<savedTab>> = daonotes.getAll()
    suspend fun insert(history: savedTab){
        daonotes.insert(history);
      daonotes.getAll()
    }
    suspend fun delete(history: savedTab){
        daonotes.delete(history)
    }

    suspend fun deleteAll(){
        daonotes.deleteAll()
    }




}