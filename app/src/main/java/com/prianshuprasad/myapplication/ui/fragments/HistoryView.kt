package com.prianshuprasad.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prianshuprasad.myapplication.App
import com.prianshuprasad.myapplication.ui.activity.MainActivity2
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.ui.adapter.HistoryViewAdapter
import com.prianshuprasad.myapplication.utils.Database.histroryDataBase.BrowsingHistory
import com.prianshuprasad.myapplication.utils.Database.histroryDataBase.Historyviewholder
//import com.prianshuprasad.myapplication.Database.histroryDataBase.BrowsingHistory
//import com.prianshuprasad.myapplication.Database.histroryDataBase.Historyviewholder
import kotlinx.android.synthetic.main.fragment_history_view.view.*
import kotlinx.android.synthetic.main.fragment_tab.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryView.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryView(listner: MainActivity2) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val listner= listner


    private lateinit var application:App
    private lateinit var historyviewholder: Historyviewholder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val binding=inflater.inflate(R.layout.fragment_history_view, container, false)
         application= App()
        historyviewholder= Historyviewholder(application)
        val mAdapter= HistoryViewAdapter(this)
        val rcview:RecyclerView= binding.findViewById(R.id.history_rcView)
        rcview.layoutManager= LinearLayoutManager(requireContext())
        rcview.adapter= mAdapter

        historyviewholder.allnotes.observeForever {
//            Toast.makeText(context,"${it.size}",Toast.LENGTH_SHORT).show()
            mAdapter.update(it as ArrayList<BrowsingHistory>)
        }







        return binding.rootView
    }

    fun openHistoryTab(url:String){
        listner.homeFragment.addNewSession(url = url)
        listner.openWebView()
    }

    fun deleteHistory(browsingHistory: BrowsingHistory){
        historyviewholder.deletenote(browsingHistory)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryView.
         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            HistoryView().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}