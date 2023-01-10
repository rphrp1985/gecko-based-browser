package com.prianshuprasad.myapplication.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.prianshuprasad.myapplication.utils.browser.Browser
import com.prianshuprasad.myapplication.ui.activity.MainActivity2
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.utils.Database.siteDatabase.SiteDataviewholder

//import com.prianshuprasad.myapplication.Database.siteDatabase.SiteDataviewholder

class SettingsPrivacyFragment(browser: Browser, listner: MainActivity2, siteDataviewholder: SiteDataviewholder) : PreferenceFragmentCompat() {
val browser=browser
    val listner = listner
    val siteDataviewholder= siteDataviewholder
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_privacy_prefrences, rootKey)

        val https = findPreference<SwitchPreferenceCompat>("HttpsOnly")
        val notTrack = findPreference<SwitchPreferenceCompat>("notTrack")
        val history = findPreference<SwitchPreferenceCompat>("historyMode")
        val biometric = findPreference<SwitchPreferenceCompat>("biometrics")
        val autocomplete = findPreference<SwitchPreferenceCompat>("autocomplete")
        val password = findPreference<SwitchPreferenceCompat>("autopasword")
        val viewlogin = findPreference<Preference>("viewlogins")
        val viewcard = findPreference<Preference>("viewcards")
        val autocard = findPreference<SwitchPreferenceCompat>("autocard")
        val viewaddress = findPreference<Preference>("viewadress")
        val autoAdress = findPreference<SwitchPreferenceCompat>("autoadress")

        val cleardata = findPreference<Preference>("clarData")



        https?.isChecked = browser.settingsData.privHttpsOnly != 0
        notTrack?.isChecked = browser.settingsData.privNotTrack != 0
        history?.isChecked = browser.settingsData.privHistory != 0
        biometric?.isChecked = browser.settingsData.privFingerprint != 0
        autocomplete?.isChecked = browser.settingsData.privAutoComplete != 0
        password?.isChecked= browser.settingsData.autoSavePassword!=0
        autocard?.isChecked = browser.settingsData.autoSaveCard!=0
        autoAdress?.isChecked = browser.settingsData.autoSaveAdress!=0




        https!!.setOnPreferenceClickListener {

            if(browser.settingsData.privHttpsOnly==0) {
                browser.settingsData.privHttpsOnly = 1
                browser.setHttpsOnly(2)

            }
            else{
                browser.settingsData.privHttpsOnly=0
                browser.setHttpsOnly(0)
                }

             siteDataviewholder.insertnote(browser.settingsData)


            return@setOnPreferenceClickListener true

        }
        notTrack!!.setOnPreferenceClickListener {

            if(browser.settingsData.privNotTrack==0) {
                browser.settingsData.privNotTrack = 1
                browser.setAntiTracking(true)

            }
            else{
                browser.settingsData.privNotTrack=0
                browser.setAntiTracking(false)
            }

            siteDataviewholder.insertnote(browser.settingsData)


            return@setOnPreferenceClickListener true

        }

        history!!.setOnPreferenceClickListener {

            if(browser.settingsData.privHistory==0) {
                browser.settingsData.privHistory = 1


            }
            else{
                browser.settingsData.privHistory=0

            }

            siteDataviewholder.insertnote(browser.settingsData)


            return@setOnPreferenceClickListener true

        }
        autocomplete!!.setOnPreferenceClickListener {

            if(browser.settingsData.privAutoComplete==0) {
                browser.settingsData.privAutoComplete = 1


            }
            else{
                browser.settingsData.privAutoComplete=0

            }

            siteDataviewholder.insertnote(browser.settingsData)


            return@setOnPreferenceClickListener true

        }

        if(Build.VERSION.SDK_INT<23){
            biometric!!.isVisible=false
        }

        biometric!!.setOnPreferenceClickListener {

            if(browser.settingsData.privFingerprint==0) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(listner.checkBiometricSupport()){
                        browser.settingsData.privFingerprint = 1
                    }else{
                        listner.notifyUser("Biometrics Authentication not availiable")
                        return@setOnPreferenceClickListener false

                    }

                }else{
                    listner.notifyUser("Biometrics Authentication not availiable")
                    return@setOnPreferenceClickListener false

                }




            }
            else{
                browser.settingsData.privFingerprint=0

            }

            siteDataviewholder.insertnote(browser.settingsData)


            return@setOnPreferenceClickListener true


        }


        cleardata!!.setOnPreferenceClickListener {


            listner.openClearDataFragment()

            return@setOnPreferenceClickListener true
        }

        password!!.setOnPreferenceClickListener {

            if(browser.settingsData.autoSavePassword==0) {
                browser.settingsData.autoSavePassword = 1


            }
            else{
                browser.settingsData.autoSavePassword=0

            }

            siteDataviewholder.insertnote(browser.settingsData)


            return@setOnPreferenceClickListener true
        }

        viewlogin!!.setOnPreferenceClickListener {

            listner.openViewLogins("Password")
            return@setOnPreferenceClickListener true
        }


        autocard!!.setOnPreferenceClickListener {

            if(browser.settingsData.autoSaveCard==0) {
                browser.settingsData.autoSaveCard = 1


            }
            else{
                browser.settingsData.autoSaveCard=0

            }

            siteDataviewholder.insertnote(browser.settingsData)


            return@setOnPreferenceClickListener true
        }

        viewcard!!.setOnPreferenceClickListener {

            listner.openViewLogins("Card")
            return@setOnPreferenceClickListener true
        }

        autoAdress!!.setOnPreferenceClickListener {

            if(browser.settingsData.autoSaveAdress==0) {
                browser.settingsData.autoSaveAdress = 1


            }
            else{
                browser.settingsData.autoSaveAdress=0

            }

            siteDataviewholder.insertnote(browser.settingsData)


            return@setOnPreferenceClickListener true
        }

        viewaddress!!.setOnPreferenceClickListener {

            listner.openViewLogins("Address")
            return@setOnPreferenceClickListener true
        }















    }


}