package com.prianshuprasad.myapplication.offlinePagesDataBase

import androidx.lifecycle.LiveData

class Offlinerespositary(private  val daonotes: OfflinePageDAO) {

    var allnotes:LiveData<List<offlinePage>> = daonotes.getAll()
    suspend fun insert(history: offlinePage){
        daonotes.insert(history);
      daonotes.getAll()
    }
    suspend fun delete(history: offlinePage){
        daonotes.delete(history)
    }

    suspend fun deleteAll(){
        daonotes.deleteAll()
    }




}