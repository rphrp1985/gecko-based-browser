package com.prianshuprasad.myapplication.fragments

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.prianshuprasad.myapplication.MainActivity2
import com.prianshuprasad.myapplication.R

class PermissionSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_permission_prefrences, rootKey)

        val siteNotification = findPreference<Preference>("site_notifications_permissions")
        val siteCamera = findPreference<Preference>("site_camera_permissions")
        val microphone = findPreference<Preference>("site_microphone_permissions")
        val location = findPreference<Preference>("site_location_permissions")
        val mediaAutoplay = findPreference<Preference>("site_media_autoplay_permissions")
        val storage = findPreference<Preference>("site_storage_permissions")
        val cookies = findPreference<Preference>("site_cookies_permissions")
        val javascript = findPreference<Preference>("site_javascript_permissions")
        val desktop = findPreference<Preference>("site_desktop_permissions")

        siteNotification!!.setOnPreferenceClickListener {

            (activity as MainActivity2).sitePermissionFragmentModeSet("Notification")
            (activity as MainActivity2).openSettingsSiteNotification()


            return@setOnPreferenceClickListener true

        }

        siteCamera!!.setOnPreferenceClickListener {

            (activity as MainActivity2).sitePermissionFragmentModeSet("Camera")
            (activity as MainActivity2).openSettingsSiteNotification()

            return@setOnPreferenceClickListener true
        }


      microphone!!.setOnPreferenceClickListener {
          (activity as MainActivity2).sitePermissionFragmentModeSet("Microphone")
          (activity as MainActivity2).openSettingsSiteNotification()

          return@setOnPreferenceClickListener true

      }

        location!!.setOnPreferenceClickListener {
            (activity as MainActivity2).sitePermissionFragmentModeSet("Location")
            (activity as MainActivity2).openSettingsSiteNotification()

            return@setOnPreferenceClickListener true
        }

        mediaAutoplay!!.setOnPreferenceClickListener {

            (activity as MainActivity2).sitePermissionFragmentModeSet("Media Autoplay")
            (activity as MainActivity2).openSettingsSiteNotification()
            return@setOnPreferenceClickListener true
        }
        storage!!.setOnPreferenceClickListener {

            (activity as MainActivity2).sitePermissionFragmentModeSet("Storage")
            (activity as MainActivity2).openSettingsSiteNotification()
            return@setOnPreferenceClickListener true
        }

        cookies!!.setOnPreferenceClickListener {

            (activity as MainActivity2).sitePermissionFragmentModeSet("Cookies")
            (activity as MainActivity2).openSettingsSiteNotification()
            return@setOnPreferenceClickListener true
        }

        javascript!!.setOnPreferenceClickListener {

            (activity as MainActivity2).sitePermissionFragmentModeSet("JavaScript")
            (activity as MainActivity2).openSettingsSiteNotification()
            return@setOnPreferenceClickListener true
        }

        desktop!!.setOnPreferenceClickListener {

            (activity as MainActivity2).sitePermissionFragmentModeSet("Desktop Mode")
            (activity as MainActivity2).openSettingsSiteNotification()
            return@setOnPreferenceClickListener true
        }







    }
}