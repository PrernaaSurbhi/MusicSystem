package com.example.musicsystem.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.musicsystem.R

/**
 * Created by PrernaSurbhi on 17/02/22.
 */
class MyService: Service() {
    lateinit var mediaPlayer:MediaPlayer

    override fun onCreate() {
        Toast.makeText(this,"Service Created",Toast.LENGTH_LONG).show()
        Log.d("MyService","onCreate")
        mediaPlayer = MediaPlayer.create(this, R.raw.beautiful_dream)
        mediaPlayer.isLooping = false
    }
    override fun onBind(intent: Intent?): IBinder? {
        Log.d("MyService","onBind")
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this,"Service Started",Toast.LENGTH_LONG).show()
         mediaPlayer.start()
        Log.d("MyService","onStartCommand")
        return START_NOT_STICKY
    }


    override fun onDestroy() {
        Toast.makeText(this,"Service Started",Toast.LENGTH_LONG).show()
        Log.d("MyService","onDestroy")
        mediaPlayer.stop()
    }

}