package com.prianshuprasad.myapplication.MediaNotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.prianshuprasad.myapplication.MainActivity2
import org.mozilla.geckoview.MediaSession

class MyBrodCastReciver(
    listner: MainActivity2,
    mediaSession: MediaSession,
    mediaNotification: MediaNotification
): BroadcastReceiver() {
    val listner= listner
    val mediaSession= mediaSession
    val mediaNotification= mediaNotification
    override fun onReceive(context: Context?, intent: Intent?) {

       mediaNotification.onClicked()
    }

}