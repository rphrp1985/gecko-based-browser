package com.prianshuprasad.myapplication.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.prianshuprasad.myapplication.MainActivity2
import com.prianshuprasad.myapplication.R
import kotlin.concurrent.thread

class notificationServices() : Service() {

    private lateinit var notificationManager: NotificationManagerCompat
    var channelId = "Progress Notification" as String

    var notitext=""

    lateinit var notification : NotificationCompat.Builder

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {


        channelId = "${(0..20000000).random()}"
        createNotificationChannel()

        notificationManager = NotificationManagerCompat.from(this)
//        startNotification()






        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()


    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }



    public fun startNotification(){

        val intent = Intent(this, MainActivity2::class.java).apply{
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
//                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent, 0)

        //Sets the maximum progress as 100

        //Creating a notification and setting its various attributes
        notification =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_baseline_add_box_24)
                .setContentTitle("Testing")
                .setContentText("tst")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(false)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        //Initial Alert
        notificationManager.notify((0..1000000).random(), notification.build())





    }




    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channelId
            val descriptionText = "RPPPPPP"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
