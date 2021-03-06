package com.example.androidcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*

@SuppressLint("MissingPermission")
class FusedLocation private constructor(context: Context) : LocationCallback() {
    private val TAG = FusedLocation::class.java.simpleName

    private val TIME_BETWEEN_UPDATES = 20 * 1000L

    @SuppressLint("VisibleForTests")
    private var client = FusedLocationProviderClient(context)

    private var locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = TIME_BETWEEN_UPDATES
    }

    override fun onLocationResult(locationResult: LocationResult) {
        Log.i(TAG, locationResult.lastLocation.toString())
        notifyListeners(locationResult)
    }

    init {
        //instancia objecto que permite definir as configs
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        //aplicar as configs ao servico de localizacao
        LocationServices.getSettingsClient(context)
            .checkLocationSettings(locationSettingsRequest)

        client.requestLocationUpdates(locationRequest, this, Looper.getMainLooper())
    }

    companion object {
        private var listeners: MutableList<OnLocationChangedListener> = mutableListOf()
        private var instance: FusedLocation? = null

        fun registerListener(listener: OnLocationChangedListener) {
            Log.i(FusedLocation::class.java.simpleName, "Listener Registered")
            listeners.add(listener)
        }

        fun unregisterListener(listener: OnLocationChangedListener) {
            listeners.remove(listener)
            Log.i(FusedLocation::class.java.simpleName, "Listener Removed")
        }

        fun notifyListeners(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            listeners.forEach { it.onLocationChanged(location.latitude, location.longitude) }
        }

        fun start(context: Context) {
            instance = if (instance == null) FusedLocation(context) else instance
        }
    }
}