package com.prianshuprasad.myapplication.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.*
import com.prianshuprasad.myapplication.App
import com.prianshuprasad.myapplication.utils.browser.Browser
import com.prianshuprasad.myapplication.ui.activity.MainActivity2
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.utils.Database.siteDatabase.SiteDataviewholder

//import com.prianshuprasad.myapplication.Database.siteDatabase.SiteDataviewholder

class SettingsFragment(browser: Browser, siteDataviewholder: SiteDataviewholder, prefs:SharedPreferences) : PreferenceFragmentCompat() {

    val browser= browser
    val siteDataviewholder = siteDataviewholder
    val prefs= prefs
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)


        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        val sitePermission = findPreference<Preference>("site_permissions")
        val searchEngine = findPreference<ListPreference>("searchEngine")
        val privacy = findPreference<Preference>("privacy")
        val swipe = findPreference<SwitchPreferenceCompat>("swipeRefresh")
        val savetabs =findPreference<SwitchPreferenceCompat>("tabClosing")
        val accesbility = findPreference<Preference>("site_accesbility")
        val nightMode= findPreference<SwitchPreferenceCompat>("night_mode")
         var isNightMode= false

        val myEdit: SharedPreferences.Editor = prefs.edit()



        isNightMode= (activity as MainActivity2).getNightMode()

        nightMode!!.isChecked= isNightMode
        savetabs!!.isChecked = (browser.settingsData.saveTabs==1)
        swipe!!.isChecked = (browser.settingsData.swipeRefresh==1)

        privacy!!.setOnPreferenceClickListener {

            (activity as MainActivity2).openSettingsPrivacyFragment()
            return@setOnPreferenceClickListener true
        }




        accesbility!!.setOnPreferenceClickListener {
            (activity as MainActivity2).openAccesbility()
            return@setOnPreferenceClickListener true
        }

        sitePermission!!.setOnPreferenceClickListener {

            (activity as MainActivity2).openPermissionSettings()

            return@setOnPreferenceClickListener true
        }

        when(browser.settingsData.defEngineName){
            "GOOGLE"-> searchEngine!!.setValueIndex(0);
            "DUCK DUCK GO"-> searchEngine!!.setValueIndex(1);
            "MSN"-> searchEngine!!.setValueIndex(2);
            "YAHOO"-> searchEngine!!.setValueIndex(3);
        }

        searchEngine!!.setOnPreferenceChangeListener(object : Preference.OnPreferenceChangeListener{

            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
             var name=""
             var url=""

                when(newValue.toString()){
                    "GOOGLE"->{
                        name="GOOGLE"
                        url= "https://www.google.com/search?q="
                    }
                    "DUCK DUCK GO"->{
                        name = newValue.toString()
                        url ="https://duckduckgo.com/?q="
                    }
                    "MSN"->{
                        name = newValue.toString()
                        url ="https://www.bing.com/search?q="


                    }
                    "YAHOO"->{
                        name = newValue.toString()
                        url ="https://search.yahoo.com/search?p="
                    }
                }

                browser.settingsData.defEngineName= name
                browser.settingsData.defEngineUrl= url

                siteDataviewholder.insertnote(browser.settingsData)


                return true
            }

        })

        swipe!!.setOnPreferenceClickListener {

            if(browser.settingsData.swipeRefresh==1)
                browser.settingsData.swipeRefresh=0;
            else
                browser.settingsData.swipeRefresh=1;

            SiteDataviewholder(App()).insertnote(browser.settingsData)

            return@setOnPreferenceClickListener true
        }

        savetabs!!.setOnPreferenceClickListener {

            if(browser.settingsData.saveTabs==1)
                browser.settingsData.saveTabs=0;
            else
                browser.settingsData.saveTabs=1;

            SiteDataviewholder(App()).insertnote(browser.settingsData)

            return@setOnPreferenceClickListener true
        }

        nightMode!!.setOnPreferenceClickListener {

            isNightMode= !isNightMode

            myEdit.putBoolean("IS_Night",isNightMode)
            myEdit.commit()


            builder.setMessage("Changes Will Effect After restart of Browser")

            builder.setTitle("Alert !")
            builder.setCancelable(true)
            builder.setPositiveButton("Restart Now",
                DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->

                    (activity as MainActivity2).triggerRebirth(requireContext())


                } as DialogInterface.OnClickListener)

            builder.setNegativeButton("Restart Later",
                DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->


                } as DialogInterface.OnClickListener)


            val alertDialog: AlertDialog = builder.create()

            alertDialog.show()





            return@setOnPreferenceClickListener true
        }




    }
}