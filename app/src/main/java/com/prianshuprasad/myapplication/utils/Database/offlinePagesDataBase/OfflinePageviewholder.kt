package com.prianshuprasad.myapplication.utils.Database.offlinePagesDataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OfflinePageviewholder(apllication: Application): AndroidViewModel(apllication) {

    val repo: Offlinerespositary
    var allnotes: LiveData<List<offlinePage>>

    init {
        val dao= OfflibePagedatabase.getDatabase(apllication).notesDao()
         repo= Offlinerespositary(dao)
        allnotes= repo.allnotes

    }

    fun deletenote(history: offlinePage)= viewModelScope.launch(Dispatchers.IO) {
        repo.delete(history)
    }

    fun insertnote(history: offlinePage)=viewModelScope.launch {
        repo.insert(history)
    }
    fun deleteAll()= viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }


}