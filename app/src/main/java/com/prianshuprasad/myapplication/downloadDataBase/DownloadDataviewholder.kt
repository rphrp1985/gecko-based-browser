package com.prianshuprasad.myapplication.downloadDataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DownloadDataviewholder(apllication: Application): AndroidViewModel(apllication) {

    val repo:DownloadDatarespositary
    var allnotes: LiveData<List<DownloadData>>

    init {
        val dao= DownloadDatadatabase.getDatabase(apllication).notesDao()
         repo= DownloadDatarespositary(dao)
        allnotes= repo.allnotes

    }

    fun deletenote(history: DownloadData)= viewModelScope.launch(Dispatchers.IO) {


        repo.delete(history)
    }

    fun insertnote(history: DownloadData)=viewModelScope.launch {
        repo.insert(history)
    }

    fun deleteAll()= viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }


}