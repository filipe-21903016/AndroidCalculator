package com.example.androidcalculator

interface OnBatteryCurrentListener {
    fun onCurrentChanged(current: Double)
    fun onCapacityChanged(capacity: Int)
}