package com.example.musicsystem.service

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Binder
import android.os.IBinder
import androidx.core.app.ActivityCompat

/**
 * Created by PrernaSurbhi on 18/02/22.
 */
class DistanceService:Service() {
    private var lastLocation: Location? = null
    var distanceInMeters:Double? = null

    val binder:IBinder by lazy{
        DistanceServiceBinder()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        val locationManager:LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = object:LocationListener{
            override fun onLocationChanged(location: Location) {
                if(lastLocation == null){
                    lastLocation = location
                }
                distanceInMeters = distanceInMeters?.plus(location.distanceTo(lastLocation))
                lastLocation = location
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1F,locationListener)

    }

    fun getMiles():Double?{
        return this.distanceInMeters?.div(1609.344)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    class DistanceServiceBinder:Binder(){
        fun getDistanceService():DistanceService{
            return DistanceService()
        }
    }
}