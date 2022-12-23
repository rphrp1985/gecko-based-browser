package com.prianshuprasad.myapplication.autocompleteDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AutocompleteDataviewholder(apllication: Application): AndroidViewModel(apllication) {

    val repo:AutocompleteDatarespositary
    var allnotes: LiveData<List<AutoCompleteData>>

    init {
        val dao= AutocompleteDatadatabase.getDatabase(apllication).notesDao()
         repo= AutocompleteDatarespositary(dao)
        allnotes= repo.allnotes

    }

    fun deletenote(history: AutoCompleteData)= viewModelScope.launch(Dispatchers.IO) {


        repo.delete(history)
    }

    fun insertnote(history: AutoCompleteData)=viewModelScope.launch {
        repo.insert(history)
    }

    fun deleteAll()= viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }


}