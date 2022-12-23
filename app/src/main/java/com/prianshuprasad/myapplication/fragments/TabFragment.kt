package com.prianshuprasad.myapplication.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.prianshuprasad.myapplication.*
import com.prianshuprasad.myapplication.viewmodels.TabViewModel
import org.mozilla.geckoview.GeckoSession

class TabFragment(browser: Browser) : Fragment() {

    private lateinit var viewModel: TabViewModel
    val browser= browser
    private lateinit var backButton: ImageView
    private lateinit var rcView: RecyclerView
    private lateinit var mAdapter: TabAdapter
    private lateinit var navigation: BottomNavigationView
    private var convertIndex:ArrayList<Int> = ArrayList()
//    private var sessionList = mutableListOf<GeckoSession>()
//    var currIndex=0;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding= inflater.inflate(R.layout.fragment_tab, container, false)

        rcView= binding.findViewById(R.id.rcView)
        backButton= binding.findViewById(R.id.back)
        navigation= binding.findViewById(R.id.navigation)
        mAdapter= TabAdapter(this)



        rcView.layoutManager = GridLayoutManager(requireContext(),2)
        rcView.adapter= mAdapter

//           navigation.selectedItemId= R.id.nav_normal
            adapterUpdate(R.id.nav_normal)
        navigation.id= R.id.nav_normal


        navigation.setOnNavigationItemSelectedListener {

            adapterUpdate(it.itemId)

//           Toast.makeText(requireContext(),"${it.itemId==R.id.nav_normal}    ${it.itemId==R.id.nav_private}",Toast.LENGTH_SHORT).show()
            return@setOnNavigationItemSelectedListener true

        }

        return binding.rootView
    }


    fun adapterUpdate(it: Int){
        var templist = ArrayList<GeckoSession>()
        if(it== R.id.nav_normal){
            convertIndex.clear()
            templist.clear()
            for( i in 0..browser.sessionList.size-1)
            {
                if(!browser.isAnonymousList[i]) {
                    templist.add(browser.sessionList[i])
                    convertIndex.add(i);
                }
            }
            mAdapter.updatenews(templist,browser)

        }else
        {
            convertIndex.clear()
            templist.clear()
            for( i in 0..browser.sessionList.size-1)
            {
                if(browser.isAnonymousList[i]) {
                    templist.add(browser.sessionList[i])
                    convertIndex.add(i)
                }


            }


            mAdapter.updatenews(templist,browser)

        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TabViewModel::class.java)
        // TODO: Use the ViewModel
    }



    fun removeTab(inde:Int){
        val index= convertIndex[inde]
        browser.sessionList.removeAt(index)
        browser.isAnonymousList.removeAt(index)
        browser.naviggationDelegateList.removeAt(index)
        browser.sessionRuntime.removeAt(index)
        browser.sessionImage.removeAt(index)


        browser.currIndex= Math.min(browser.sessionList.size-1,browser.currIndex)

        adapterUpdate(navigation.selectedItemId)



    }

    fun openWeb(inde:Int){
        val index= convertIndex[inde]
        browser.currIndex= index

        (activity as MainActivity2).openWebView()
    }





    }