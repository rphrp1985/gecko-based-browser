package com.prianshuprasad.myapplication.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.prianshuprasad.myapplication.App
import com.prianshuprasad.myapplication.Browser
import com.prianshuprasad.myapplication.MainActivity2
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.siteDatabase.SiteDataviewholder

class SettingsFragment(browser: Browser,siteDataviewholder: SiteDataviewholder) : PreferenceFragmentCompat() {

    val browser= browser
    val siteDataviewholder = siteDataviewholder
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

          val sitePermission = findPreference<Preference>("site_permissions")
        val searchEngine = findPreference<ListPreference>("searchEngine")
        val privacy = findPreference<Preference>("privacy")
        val swipe = findPreference<SwitchPreferenceCompat>("swipeRefresh")
        val savetabs =findPreference<SwitchPreferenceCompat>("tabClosing")





        savetabs!!.isChecked = (browser.settingsData.saveTabs==1)
        swipe!!.isChecked = (browser.settingsData.swipeRefresh==1)


        privacy!!.setOnPreferenceClickListener {

            (activity as MainActivity2).openSettingsPrivacyFragment()
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





    }
}