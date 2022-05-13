package com.example.androidcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*

@SuppressLint("MissingPermission")
class FusedLocation private constructor(context: Context) : LocationCallback(){
    private val TAG = FusedLocation::class.java.simpleName

    private val TIME_BETWEEN_UPDATES = 20 * 1000L

    private var client = FusedLocationProviderClient(context)

    private  var locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        //interval = TIME_BETWEEN_UPDATES
        smallestDisplacement = 10f
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

    companion object{
        private var listener: OnLocationChangedListener? = null
        private var instance: FusedLocation? = null

        fun registerListener(listener: OnLocationChangedListener){
            Log.i(FusedLocation::class.java.simpleName, "Listener Registered")
            this.listener = listener
        }

        fun unregisterListener(){
            listener = null
        }

        fun notifyListeners(locationResult: LocationResult){
            val location = locationResult.lastLocation
            listener?.onLocationChanged(location.latitude, location.longitude)
        }

        fun start(context:Context)
        {
            instance = if (instance == null) FusedLocation(context) else instance
        }
    }
}