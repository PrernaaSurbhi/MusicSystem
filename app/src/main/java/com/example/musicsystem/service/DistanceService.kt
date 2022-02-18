package com.example.musicsystem.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/**
 * Created by PrernaSurbhi on 18/02/22.
 */
class DistanceService:Service() {

    val binder:IBinder by lazy{
        DistanceServiceBinder()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    class DistanceServiceBinder:Binder(){
//        fun getDistanceService():DistanceService{
//            return DistanceService
//        }
    }
}