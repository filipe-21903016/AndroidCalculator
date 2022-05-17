package com.example.androidcalculator

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.google.android.gms.location.LocationResult
import java.util.*

class Light private constructor(context: Context) : SensorEventListener {
    private val TAG = Light::class.java.simpleName
    private var sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private fun start() {
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onSensorChanged(event: SensorEvent) {
        notifyListeners(event.values)
        Log.i(TAG, Arrays.toString(event.values))
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.i(TAG, "onAccuracyChanged")
    }

    companion object{
        private var listeners: MutableList<OnLightChangedListener> = mutableListOf<OnLightChangedListener>()
        private var instance: Light? = null

        fun registerListener(listener: OnLightChangedListener){
            listeners.add(listener)
            Log.i(Light::class.java.simpleName, "New Listener: $listener")
        }

        fun unregisterListener(listener: OnLightChangedListener){
            listeners.remove(listener)
            Log.i(Light::class.java.simpleName, "Removed Listener: $listener")
        }

        fun notifyListeners(values: FloatArray){
            listeners.forEach {
                it.onLightChanged(values)
            }
        }

        fun start(context: Context){
            instance = if (instance == null) Light(context) else instance
            instance?.start()
        }
    }
}