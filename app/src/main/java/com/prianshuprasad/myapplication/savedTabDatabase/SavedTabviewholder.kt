package com.prianshuprasad.myapplication.savedTabDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedTabviewholder(apllication: Application): AndroidViewModel(apllication) {

    val repo:SavedTabrespositary
    var allnotes: LiveData<List<savedTab>>

    init {
        val dao= SavedTabdatabase.getDatabase(apllication).notesDao()
         repo= SavedTabrespositary(dao)
        allnotes= repo.allnotes

    }

    fun deletenote(history: savedTab)= viewModelScope.launch(Dispatchers.IO) {
        repo.delete(history)
    }

    fun insertnote(history: savedTab)=viewModelScope.launch {
        repo.insert(history)
    }
    fun deleteAll()= viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }


}