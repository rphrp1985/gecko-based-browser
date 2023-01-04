package com.prianshuprasad.myapplication.utils.Database.bookmarkDatabase

import androidx.lifecycle.LiveData

class BookmarkDatarespositary(private  val daonotes: BookmarkDataDAO) {

    var allnotes:LiveData<List<BookmarkData>> = daonotes.getAll()
    suspend fun insert(history: BookmarkData){
        daonotes.insert(history);
      daonotes.getAll()
    }
    suspend fun delete(history: BookmarkData){
        daonotes.delete(history)
    }
    suspend fun deleteAll(){
        daonotes.deleteAll()
    }




}