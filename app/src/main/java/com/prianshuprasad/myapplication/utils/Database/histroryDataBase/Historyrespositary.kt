package com.prianshuprasad.myapplication.utils.Database.histroryDataBase

import androidx.lifecycle.LiveData

class Historyrespositary(private  val daonotes: HistoryDAO) {

    var allnotes:LiveData<List<BrowsingHistory>> = daonotes.getAll()
    suspend fun insert(history: BrowsingHistory){
        daonotes.insert(history);
      daonotes.getAll()
    }
    suspend fun delete(history: BrowsingHistory){
        daonotes.delete(history)
    }

    suspend fun deleteAll(){
        daonotes.deleteAll()
    }




}