package com.prianshuprasad.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.slider.RangeSlider
import com.prianshuprasad.myapplication.ui.activity.MainActivity2
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.utils.Database.siteDatabase.SiteDataviewholder
//import com.prianshuprasad.myapplication.Database.siteDatabase.SiteDataviewholder
//import kotlinx.coroutines.DefaultExecutor.thread
import kotlin.concurrent.thread

class AccessbilityFragment(listner: MainActivity2, siteDataviewholder: SiteDataviewholder) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    val siteDataviewholder = siteDataviewholder
    val listner = listner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding= inflater.inflate(R.layout.fragment_accessbility, container, false)

        val toolbar:android.widget.Toolbar = binding.findViewById(R.id.toolbar)
        val forceZoom:SwitchCompat = binding.findViewById(R.id.force_zoom)

        toolbar.setNavigationOnClickListener {
            listner.onBackPressed()
        }

        val slider:RangeSlider = binding.findViewById(R.id.slider)
        slider.stepSize=0.25f
        slider.haloRadius= 4

       slider.values= mutableListOf(listner.browser.settingsData.fontSize)
        slider.valueFrom=.5f
        slider.valueTo= 2.0f
        slider.addOnChangeListener( object :RangeSlider.OnChangeListener{
            override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {

               listner.browser.settingsData.fontSize= value
                siteDataviewholder.insertnote(listner.browser.settingsData)

                thread {
                    for (i in 0..listner.browser.sessionRuntime.size - 1) {
                        listner.browser.sessionRuntime[i].settings.fontSizeFactor = value
                        listner.browser.sessionList[i].reload()
                    }
                }

            }

        })


        forceZoom.isChecked= (listner.browser.settingsData.force_zoom==1)

        forceZoom.setOnClickListener {
            if(forceZoom.isChecked)
                listner.browser.settingsData.force_zoom=1;
            else
                listner.browser.settingsData.force_zoom=0

            thread {
                for (i in 0..listner.browser.sessionRuntime.size - 1) {
                    listner.browser.sessionRuntime[i].settings.forceUserScalableEnabled = listner.browser.settingsData.force_zoom==1

                    listner.browser.sessionList[i].reload()
                }
            }





            siteDataviewholder.insertnote(listner.browser.settingsData)
        }



        return binding.rootView
    }


}