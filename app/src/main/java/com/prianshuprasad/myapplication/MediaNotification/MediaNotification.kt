package com.prianshuprasad.myapplication.MediaNotification

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.prianshuprasad.myapplication.*
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.Image
import org.mozilla.geckoview.MediaSession

class MediaNotification(listner: MainActivity2, isPlaying:MutableLiveData<Boolean> ) {
    val listner= listner
    private lateinit var notification:NotificationCompat.Builder
     private lateinit var  mediaSessiono:MediaSession
     val isPlaying = isPlaying
    lateinit var pendingIntent: PendingIntent
    var id=0;




    fun ShowNotification(session: GeckoSession, mediasession: MediaSession) {

        mediaSessiono= mediasession
        listner.createNotificationChannel("${mediaSessiono.hashCode()}")
        val intent = Intent()
        intent.setAction("pause")

        val mediaStyle: androidx.media.app.NotificationCompat.MediaStyle =
            androidx.media.app.NotificationCompat.MediaStyle()


         pendingIntent = PendingIntent.getBroadcast(
            listner, 0,intent,0)
        val filter = IntentFilter("pause")
        listner.registerReceiver( MyBrodCastReciver(listner,mediaSessiono,this),filter)

        notification =
            NotificationCompat.Builder(listner, "media")
                .setSmallIcon(R.drawable.ic_baseline_web_24)
                .setStyle(mediaStyle)
                .setOngoing(true)
                .setContentTitle("${(session.navigationDelegate as MyNavigationDelegate).url}")
                .setContentText("${(session.contentDelegate as MyContentDelegate).title}")


//        notification.addAction(R.drawable.ic_baseline_add_box_24, "pause", pendingIntent)
        notification.addAction(com.tonyodev.fetch2.R.drawable.fetch_notification_pause,"playing",pendingIntent)
        id= (0..1000000).random()
        isPlaying.observeForever {
//            return@observeForever
            notification.clearActions()
            if(it){
                notification.addAction(com.tonyodev.fetch2.R.drawable.fetch_notification_pause,"pause",pendingIntent)

            }else
            {
                notification.addAction(R.drawable.ic_baseline_play_circle_24,"Play",pendingIntent)

            }
            listner.notificationManager.notify(id, notification.build())
        }


        listner.notificationManager.notify(id, notification.build())

    }

    fun onClicked(){

        if(!mediaSessiono.isActive){
            onDeactivate()
            return
        }

        notification.clearActions()
        if(isPlaying.value==false){
                mediaSessiono.play()
        }else{
           mediaSessiono.pause()
        }

    }


    fun onDeactivate(){
        listner.notificationManager.cancel(id)

    }


    fun setMeta(titile:String,session:GeckoSession,image:Image?){

          notification.clearActions()

        notification =
            NotificationCompat.Builder(listner, "media")
                .setSmallIcon(R.drawable.ic_baseline_web_24)
                .setOngoing(true)
                .setContentTitle("${(session.navigationDelegate as MyNavigationDelegate).url}")
                .setContentText(titile)

        if(isPlaying.value==true){
//            listner.notifyUser("$it")
            notification.addAction(com.tonyodev.fetch2.R.drawable.fetch_notification_pause,"Pause",pendingIntent)

        }else
        {
            notification.addAction(R.drawable.ic_baseline_play_circle_24,"Play",pendingIntent)

        }


        listner.notificationManager.notify(id,notification.build())
    }




}