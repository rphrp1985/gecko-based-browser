package com.prianshuprasad.myapplication.bookmarkDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarkDataviewholder(apllication: Application): AndroidViewModel(apllication) {

    val repo:BookmarkDatarespositary
    var allnotes: LiveData<List<BookmarkData>>

    init {
        val dao= BookmarkDatadatabase.getDatabase(apllication).notesDao()
         repo= BookmarkDatarespositary(dao)
        allnotes= repo.allnotes

    }

    fun deletenote(history: BookmarkData)= viewModelScope.launch(Dispatchers.IO) {


        repo.delete(history)
    }

    fun insertnote(history: BookmarkData)=viewModelScope.launch {
        repo.insert(history)
    }

    fun deleteAll()= viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }


}