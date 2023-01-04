package com.prianshuprasad.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prianshuprasad.myapplication.ui.activity.MainActivity2
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.ui.adapter.OfflinePageAdapter
import com.prianshuprasad.myapplication.utils.Database.offlinePagesDataBase.OfflinePageviewholder
import com.prianshuprasad.myapplication.utils.Database.offlinePagesDataBase.offlinePage

//import com.prianshuprasad.myapplication.Database.offlinePagesDataBase.OfflinePageviewholder
//import com.prianshuprasad.myapplication.Database.offlinePagesDataBase.offlinePage

class OfflinePagesFragment(listner: MainActivity2, offlinePageviewholder: OfflinePageviewholder) : Fragment() {

    val listner = listner
    val offlinePageviewholder = offlinePageviewholder
    private lateinit var  offlinePageAdapter: OfflinePageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        val binding= inflater.inflate(R.layout.fragment_offline_pages, container, false)

        val toolbar:android.widget.Toolbar= binding.findViewById(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            listner.onBackPressed()
        }


        offlinePageAdapter = OfflinePageAdapter(this)
        val rcview: RecyclerView = binding.findViewById(R.id.offline_pages_rcView)
        rcview.layoutManager= LinearLayoutManager(requireContext())
        rcview.adapter= offlinePageAdapter

        offlinePageviewholder.allnotes.observeForever {
//            Toast.makeText(context,"${it.size}",Toast.LENGTH_SHORT).show()
            offlinePageAdapter.update(it as ArrayList<offlinePage>)
        }








        return binding.rootView
    }

    fun openPage(url:String){
        listner.homeFragment.addNewSession(url=url)
        listner.onBackPressed()
    }

    fun delete(page: offlinePage){

        offlinePageviewholder.deletenote(page)
    }

}