package com.prianshuprasad.myapplication.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.prianshuprasad.myapplication.ui.activity.MainActivity2
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.ui.viewmodels.HistoryBookmarkViewModel

class HistoryBookmark(listner: MainActivity2) : Fragment() {

//    companion object {
//        fun newInstance() = HistoryBookmark()
//    }

    private lateinit var viewModel: HistoryBookmarkViewModel
    private lateinit var historyViewFragment: HistoryView
    private lateinit var bookmarkViewFrogment: BookmarkView
    private lateinit var frameView:FrameLayout
    private lateinit var navigation:BottomNavigationView
    private val listner= listner
    private lateinit var toolbar:Toolbar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding= inflater.inflate(R.layout.fragment_history_bookmark, container, false)

        historyViewFragment= HistoryView(listner)
        bookmarkViewFrogment= BookmarkView(listner)
        frameView= binding.findViewById<FrameLayout>(R.id.content)
        navigation= binding.findViewById(R.id.navigation)

        toolbar= binding.findViewById(R.id.toolbar)

        val fragmentTransaction = getParentFragmentManager().beginTransaction()

        fragmentTransaction.replace(R.id.content,
              historyViewFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed


        toolbar.title="History"


        navigation.id= R.id.nav_history


        navigation.setOnNavigationItemSelectedListener(){

            when(it.itemId){
                R.id.nav_history ->{
                    toolbar.title="History"
                    if(!historyViewFragment.isVisible){
                        val fragmentTransaction = getParentFragmentManager().beginTransaction()


                        fragmentTransaction.replace(R.id.content,
                            historyViewFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed



                    }


                }

               R.id.nav_bookmarks->{
                   toolbar.title="Bookmark"
                   if(!bookmarkViewFrogment.isVisible){
                       val fragmentTransaction = getParentFragmentManager().beginTransaction()

                       fragmentTransaction.replace(R.id.content,
                           bookmarkViewFrogment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed


                   }

               }
            }

            return@setOnNavigationItemSelectedListener true

        }



    return binding.rootView
    }






    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryBookmarkViewModel::class.java)





        // TODO: Use the ViewModel
    }
















}

