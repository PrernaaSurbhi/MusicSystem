package com.example.musicsystem.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.musicsystem.MainActivity
import com.example.musicsystem.R

/**
 * Created by PrernaSurbhi on 17/02/22.
 */
class MyForgroundService: Service(){

    private val contentIntent by lazy{
        PendingIntent.getActivity(this,
                      0,
                       Intent(this,MainActivity::class.java),
                       PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private val notificationManager by lazy{
        this.getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId: String? = createNotificationChannel(notificationManager)
        val notificationBuilder = channelId?.let { NotificationCompat.Builder(this, it) }

        val notification: Notification? = notificationBuilder?.setOngoing(true)
            ?.setContentTitle("Navigine Service Example")
            ?.setContentIntent(contentIntent)    
            ?.setCategory(NotificationCompat.CATEGORY_SERVICE)
            ?.build()

        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_REDELIVER_INTENT

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager): String {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.lockscreenVisibility =
            Notification.VISIBILITY_PUBLIC // will be shown in lock screen
        notificationManager.createNotificationChannel(channel)
        return CHANNEL_ID
    }


    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    companion object{
        const val CHANNEL_ID = "ForgroundNotificationId"
        const val CHANNEL_NAME = "NotificationName"
        const val CHANNEL_DESCRIPTION = "NotificationDescription"
        const val NOTIFICATION_ID = 99

    }
}
