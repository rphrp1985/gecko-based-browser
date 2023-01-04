package com.prianshuprasad.myapplication.utils.Database.siteDatabase

import androidx.lifecycle.LiveData

class SiteDatarespositary(private  val daonotes: SiteDataDAO) {

    var allnotes:LiveData<List<SiteData>> = daonotes.getAll()
    suspend fun insert(history: SiteData){
        daonotes.insert(history);
      daonotes.getAll()
    }
    suspend fun delete(history: SiteData){
        daonotes.delete(history)
    }
    suspend fun deleteAll(){
        daonotes.deleteAll()
    }




}