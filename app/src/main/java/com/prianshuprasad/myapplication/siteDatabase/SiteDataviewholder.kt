package com.prianshuprasad.myapplication.siteDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SiteDataviewholder(apllication: Application): AndroidViewModel(apllication) {

    val repo:SiteDatarespositary
    var allnotes: LiveData<List<SiteData>>

    init {
        val dao= SiteDatadatabase.getDatabase(apllication).notesDao()
         repo= SiteDatarespositary(dao)
        allnotes= repo.allnotes

    }

    fun deletenote(history: SiteData)= viewModelScope.launch(Dispatchers.IO) {


        repo.delete(history)
    }

    fun insertnote(history: SiteData)=viewModelScope.launch {
        repo.insert(history)
    }

    fun deleteAll()= viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }


}