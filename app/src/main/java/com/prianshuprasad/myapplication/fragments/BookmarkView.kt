package com.prianshuprasad.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prianshuprasad.myapplication.App
import com.prianshuprasad.myapplication.MainActivity2
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.adapter.BookmarkViewAdapter
import com.prianshuprasad.myapplication.adapter.HistoryViewAdapter
import com.prianshuprasad.myapplication.bookmarkDatabase.BookmarkData
import com.prianshuprasad.myapplication.bookmarkDatabase.BookmarkDataviewholder
import com.prianshuprasad.myapplication.histroryDataBase.BrowsingHistory
import com.prianshuprasad.myapplication.histroryDataBase.Historyviewholder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BookmarkView(listner:MainActivity2) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private lateinit var application:App
    private lateinit var bookmarkDataviewholder: BookmarkDataviewholder
    val listner= listner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        val binding=inflater.inflate(R.layout.fragment_bookmark_view, container, false)
        application= App()
        bookmarkDataviewholder= BookmarkDataviewholder(application)
        val mAdapter= BookmarkViewAdapter(this)
        val rcview: RecyclerView = binding.findViewById(R.id.history_rcView)
        rcview.layoutManager= LinearLayoutManager(requireContext())
        rcview.adapter= mAdapter
            bookmarkDataviewholder.allnotes.observeForever {
//            Toast.makeText(context,"${it.size}",Toast.LENGTH_SHORT).show()
            mAdapter.update(it as ArrayList<BookmarkData>)
        }







        return binding.rootView
    }

    fun open(url:String){

        listner.homeFragment.addNewSession(url=url)
        listner.onBackPressed()

    }


    fun delete(data:BookmarkData){
        bookmarkDataviewholder.deletenote(data)
    }



}