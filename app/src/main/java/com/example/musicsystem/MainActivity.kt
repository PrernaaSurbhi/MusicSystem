package com.example.musicsystem

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.musicsystem.databinding.ActivityMainBinding
import com.example.musicsystem.service.DistanceService
import com.example.musicsystem.service.MyForgroundService
import com.example.musicsystem.service.MyService


class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    var distanceService: DistanceService? = null
    private var bound = false

    val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            var distanceServiceBinder = binder as DistanceService.DistanceServiceBinder
            distanceService = distanceServiceBinder.getDistanceService()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.buttonStart.setOnClickListener(this)
        binding.buttonStop.setOnClickListener(this)
        binding.buttonStartForground.setOnClickListener(this)
        distanceTravelled()
    }

    override fun onStart() {
        super.onStart()
        val bindServiceIntent = Intent(this, DistanceService::class.java)
        //The code Context.BIND_AUTO_CREATE tells Android to create the service if it doesn’t already exist.
        bindService(bindServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun startBackGroundService() {
        val intent = Intent(this, MyService::class.java)
        startService(intent)
    }

    fun stopBackGroundService() {
        val intent = Intent(this, MyService::class.java)
        stopService(intent)
    }

    fun startForgoundService() {
        var foreGroundService = Intent(this, MyForgroundService::class.java)
        foreGroundService.putExtra("ForeGroundText", "Foreground Service Text")
        ContextCompat.startForegroundService(this, foreGroundService)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.buttonStart -> {
                startBackGroundService()
            }

            binding?.buttonStop -> {
                stopBackGroundService()
            }
            binding.buttonStartForground -> {
                startForgoundService()
            }
        }
    }

    fun distanceTravelled() {
        var handler = Looper.myLooper()?.let { Handler(it) }
        handler?.post(object : Runnable {
            override fun run() {
                var distance: Double? = 0.0
                if (distanceService != null) {
                    distance = distanceService?.getMiles()
                }
                binding.distancetv.text = distance.toString()
                handler.postDelayed(this, 1000)
            }
        }

        )

    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            unbindService(serviceConnection)
            bound = false
        }
    }
}