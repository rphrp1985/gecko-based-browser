package com.prianshuprasad.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prianshuprasad.myapplication.App
import com.prianshuprasad.myapplication.MainActivity2
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.adapter.SitePermissionAdapter
import com.prianshuprasad.myapplication.siteDatabase.SiteData
import com.prianshuprasad.myapplication.siteDatabase.SiteDataviewholder
import kotlinx.android.synthetic.main.fragment_tab.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SiteNotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SiteNotificationFragment(listner:MainActivity2) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var application:App
    private lateinit var notificationSwitch: SwitchCompat
    private lateinit var siteDataviewholder: SiteDataviewholder
    private var switchStatus= false;
    private lateinit var settingsdata:SiteData
    val listner = listner

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
        val binding= inflater.inflate(R.layout.fragment_site_notification, container, false)


        application= App()
        settingsdata= SiteData(coreAdress = "Settings")
        notificationSwitch= binding.findViewById(R.id.notificationSwitch)
        notificationSwitch.text = listner.sitePermissionFragmentModeGet()
        val toolbar:android.widget.Toolbar= binding.findViewById(R.id.toolbar)
        toolbar.title= listner.sitePermissionFragmentModeGet()

        val mAdapter= SitePermissionAdapter(this,listner.sitePermissionFragmentModeGet())
        val rcview: RecyclerView = binding.findViewById(R.id.rcview_site_notification)

        rcview.layoutManager= LinearLayoutManager(requireContext())
        rcview.adapter= mAdapter
        siteDataviewholder = SiteDataviewholder(application)
        siteDataviewholder.allnotes.observeForever {
            val arr:ArrayList<SiteData> = ArrayList()

            for(sitedata in it){
                if(sitedata.coreAdress=="Settings") {
                    settingsdata = sitedata
                    when (listner.sitePermissionFragmentModeGet()) {
                        "Notification" -> {
                            notificationSwitch.isChecked = (sitedata.permNoti == 1)

                            switchStatus = (sitedata.permNoti == 1)

                        }
                        "Location" -> {
                            notificationSwitch.isChecked = (sitedata.permLocation == 1)

                            switchStatus = notificationSwitch.isChecked

                        }

                        "JavaScript" -> {
                            notificationSwitch.isChecked = (sitedata.permJavaScript == 1)

                            switchStatus = notificationSwitch.isChecked

                        }

                        "Desktop Mode" -> {
                            notificationSwitch.isChecked = (sitedata.permDesktop == 1)

                            switchStatus = notificationSwitch.isChecked

                        }


                        "Camera" -> {
                            notificationSwitch.isChecked = (sitedata.permCamera == 1)

                            switchStatus = (sitedata.permCamera == 1)
                        }
                        "Microphone" -> {
                            notificationSwitch.isChecked = (sitedata.permMicrophone == 1)

                            switchStatus = (sitedata.permMicrophone == 1)
                        }


                        "Storage" -> {
                            notificationSwitch.isChecked = (sitedata.permStorage == 1)

                            switchStatus = notificationSwitch.isChecked
                        }
                        "Cookies" -> {
                            notificationSwitch.isChecked = (sitedata.permCokies == 1)

                            switchStatus = notificationSwitch.isChecked
                        }
                        "Media Autoplay" -> {
                            notificationSwitch.isChecked = (sitedata.permMedia == 1)

                            switchStatus = notificationSwitch.isChecked
                        }

                    }

                    if(switchStatus)
                        rcview.visibility= View.VISIBLE
                    else
                        rcview.visibility= View.GONE
                    if(listner.sitePermissionFragmentModeGet()=="Desktop Mode"){
                        if(switchStatus)
                            rcview.visibility= View.GONE
                        else
                            rcview.visibility= View.VISIBLE
                    }


                }else
                    arr.add(sitedata)
            }


            mAdapter.update(arr)
        }


        notificationSwitch.setOnClickListener {
            switchStatus= !switchStatus
            when(listner.sitePermissionFragmentModeGet()){
                "Notification"->{
                    if(switchStatus)
                    {
                        settingsdata.permNoti=1
                        siteDataviewholder.insertnote(settingsdata)
                    }else
                    {
                        settingsdata.permNoti=0
                        siteDataviewholder.insertnote(settingsdata)

                    }

                }
                "Location"->{
                    if(switchStatus)
                    {
                        settingsdata.permLocation=1
                        siteDataviewholder.insertnote(settingsdata)
                    }else
                    {
                        settingsdata.permLocation=0
                        siteDataviewholder.insertnote(settingsdata)

                    }

                }
                "JavaScript"->{
                    if(switchStatus)
                    {
                        settingsdata.permJavaScript=1
                        siteDataviewholder.insertnote(settingsdata)
                    }else
                    {
                        settingsdata.permJavaScript=0
                        siteDataviewholder.insertnote(settingsdata)

                    }

                }
                "Desktop Mode"->{
                    if(switchStatus)
                    {
                        settingsdata.permDesktop=1
                        siteDataviewholder.insertnote(settingsdata)
                    }else
                    {
                        settingsdata.permDesktop=0
                        siteDataviewholder.insertnote(settingsdata)

                    }

                }
                "Camera"->{
                    if(switchStatus)
                    {
                        settingsdata.permCamera=1
                        siteDataviewholder.insertnote(settingsdata)
                    }else
                    {
                        settingsdata.permCamera=0
                        siteDataviewholder.insertnote(settingsdata)

                    }
                }
                "Microphone"->{
                    if(switchStatus)
                    {
                        settingsdata.permMicrophone=1
                        siteDataviewholder.insertnote(settingsdata)
                    }else
                    {
                        settingsdata.permMicrophone=0
                        siteDataviewholder.insertnote(settingsdata)

                    }
                }

                "Media Autoplay"->{
                    if(switchStatus)
                    {
                        settingsdata.permMedia=1
                        siteDataviewholder.insertnote(settingsdata)
                    }else
                    {
                        settingsdata.permMedia=0
                        siteDataviewholder.insertnote(settingsdata)

                    }
                }
                "Storage"->{
                    if(switchStatus)
                    {
                        settingsdata.permStorage=1
                        siteDataviewholder.insertnote(settingsdata)
                    }else
                    {
                        settingsdata.permStorage=0
                        siteDataviewholder.insertnote(settingsdata)

                    }
                }
                "Cookies"->{
                    if(switchStatus)
                    {
                        settingsdata.permCokies=1
                        siteDataviewholder.insertnote(settingsdata)
                    }else
                    {
                        settingsdata.permCokies=0
                        siteDataviewholder.insertnote(settingsdata)

                    }
                }


            }



        }


        toolbar.setNavigationOnClickListener {
            listner.onBackPressed()
        }



        return binding.rootView
    }

    fun updateNotificationSettings(siteData: SiteData){
        siteDataviewholder.insertnote(siteData)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SiteNotificationFragment.
         */
        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            SiteNotificationFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}