package com.prianshuprasad.myapplication.utils.Database.histroryDataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Historyviewholder(apllication: Application): AndroidViewModel(apllication) {

    val repo: Historyrespositary
    var allnotes: LiveData<List<BrowsingHistory>>

    init {
        val dao= Historydatabase.getDatabase(apllication).notesDao()
         repo= Historyrespositary(dao)
        allnotes= repo.allnotes

    }

    fun deletenote(history: BrowsingHistory)= viewModelScope.launch(Dispatchers.IO) {
        repo.delete(history)
    }

    fun insertnote(history: BrowsingHistory)=viewModelScope.launch {
        repo.insert(history)
    }
    fun deleteAll()= viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }


}