package com.prianshuprasad.myapplication.utils.Database.downloadDataBase

import androidx.lifecycle.LiveData

class DownloadDatarespositary(private  val daonotes: DownloadDataDAO) {

    var allnotes:LiveData<List<DownloadData>> = daonotes.getAll()
    suspend fun insert(history: DownloadData){
        daonotes.insert(history);
      daonotes.getAll()
    }
    suspend fun delete(history: DownloadData){
        daonotes.delete(history)
    }
    suspend fun deleteAll(){
        daonotes.deleteAll()
    }




}