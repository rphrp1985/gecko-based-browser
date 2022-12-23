package com.prianshuprasad.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toolbar
import com.prianshuprasad.myapplication.App
import com.prianshuprasad.myapplication.Browser
import com.prianshuprasad.myapplication.MainActivity2
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.adapter.HistoryViewAdapter
import com.prianshuprasad.myapplication.histroryDataBase.Historyviewholder
import com.prianshuprasad.myapplication.siteDatabase.SiteDataviewholder
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.StorageController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClearDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClearDataFragment(listner:MainActivity2,browser: Browser) : Fragment() {
    val browser= browser
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val listner= listner

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
        val binding= inflater.inflate(R.layout.fragment_clear_data, container, false)

        val histsite= binding.findViewById<CheckBox>(R.id.hist_site)
        val cookies = binding.findViewById<CheckBox>(R.id.cookies)
        val cache = binding.findViewById<CheckBox>(R.id.cache)
        val sitesettings= binding.findViewById<CheckBox>(R.id.site_set)
        val toolbar = binding.findViewById<Toolbar>(R.id.toolbar)
        val delete = binding.findViewById<Button>(R.id.delete)

        toolbar.setNavigationOnClickListener {
            listner.onBackPressed()
        }


        delete.setOnClickListener {
            if(histsite.isChecked){
                Historyviewholder(App()).deleteAll()
                browser.sessionRuntime[0].storageController.clearData(StorageController.ClearFlags.SITE_DATA)
                browser.sessionRuntime[0].storageController.clearData(StorageController.ClearFlags.SITE_SETTINGS)


            }
            if(cookies.isChecked){
                browser.sessionRuntime[0].storageController.clearData(StorageController.ClearFlags.COOKIES)
            }
            if (cache.isChecked){
                browser.sessionRuntime[0].storageController.clearData(StorageController.ClearFlags.ALL_CACHES)

            }
            if(sitesettings.isChecked){
                SiteDataviewholder(App()).deleteAll()
            }
            listner.notifyUser("Deleted")
            listner.onBackPressed()
        }



        return binding.rootView
    }

    companion object {
    }

}