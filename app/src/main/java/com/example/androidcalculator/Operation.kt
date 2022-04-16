package com.example.androidcalculator

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

data class Operation(val expression: String, val result:Double)
{
    val timestamp : Long = Date().time

    @SuppressLint("SimpleDateFormat")
    fun getOperationDatetime(): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss")
        return simpleDateFormat.format(timestamp)
    }
}
